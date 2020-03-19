package br.com.technocorp.service;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.Linha;
import br.com.technocorp.dao.CoordinateDAO;
import br.com.technocorp.dao.LinhaDAO;
import br.com.technocorp.form.IntinerarioCoordinateForm;
import com.google.gson.JsonElement;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.ApiIntinerario;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class IntinerarioService {
    @Autowired
    private LinhaDAO linhaDAO;
    @Autowired
    private CoordinateDAO coordinateDAO;


    public void getAll() {

        for (Linha linha : linhaDAO.findAll()
        ) {
            try {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.poatransporte.com.br/php/facades/process.php/")

                        .build();

                ApiIntinerario api = retrofit.create(ApiIntinerario.class);

                Response<ResponseBody> response = api.getIntinerario("il", linha.getIdLinha()).execute();

                InputStream is = response.body().byteStream();

                try (InputStreamReader streamReader = new InputStreamReader(is)) {
                    BufferedReader reader = new BufferedReader(streamReader);

                    JSONParser jsonParser = new JSONParser();
                    JSONObject obj = (JSONObject) jsonParser.parse(reader);
                    Set<Map.Entry<String, JsonElement>> set = obj.entrySet();




                    for (Object je : set) {

                        Map.Entry<String, JSONObject> hashMap = (Map.Entry<String, JSONObject>) je;


                        try {
                            Coordinate c = new Coordinate();
                            c.setLinha(linha);
                            try {
                                c.setLat(hashMap.getValue().get("lat").toString());
                                c.setLng(hashMap.getValue().get("lng").toString());
                            } catch (ClassCastException e1) {
                            }


                            if (c.getLat() != null && c.getLng() != null) {

                                if (coordinateDAO.findByLatAndLgnAndIdLinha(c.getLinha().getIdLinha(),
                                        c.getLat(), c.getLng()).size() == 0) {
                                    coordinateDAO.save(c);
                                }
                            }


                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        }
                    }
                    reader.close();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public List<Coordinate> findAllWeb(String codigo) {


        List<Coordinate> listCoordinate;
        listCoordinate = new ArrayList<>();

        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.poatransporte.com.br/php/facades/process.php/")

                    .build();

            ApiIntinerario api = retrofit.create(ApiIntinerario.class);

            Response<ResponseBody> response = api.getIntinerario("il", new Integer(codigo)).execute();

            InputStream is = response.body().byteStream();

            try (InputStreamReader streamReader = new InputStreamReader(is)) {
                BufferedReader reader = new BufferedReader(streamReader);

                JSONParser jsonParser = new JSONParser();
                JSONObject obj = (JSONObject) jsonParser.parse(reader);
                Set<Map.Entry<String, JsonElement>> set = obj.entrySet();


                for (Object je : set) {

                    Map.Entry<String, JSONObject> hashMap = (Map.Entry<String, JSONObject>) je;


                    try {

                        Coordinate c = new Coordinate();
                        c.setLat(hashMap.getValue().get("lat").toString());
                        c.setLng(hashMap.getValue().get("lng").toString());

                        listCoordinate.add(c);
                    } catch (ClassCastException e) {

                    }
                }
                reader.close();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return listCoordinate;
    }






    public List<Coordinate> findAllIntinerario(IntinerarioCoordinateForm form) {
        List<Coordinate> listCoordinate = new ArrayList<>();

        for (Coordinate c : coordinateDAO.findIdLinha(Integer.parseInt(form.getCodigo()))) {
            listCoordinate.add(c.toSingleCoordinate());
        }
        return listCoordinate;
    }

}
