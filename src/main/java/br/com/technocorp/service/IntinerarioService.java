package br.com.technocorp.service;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.Linha;
import br.com.technocorp.dao.CoordinateDAO;
import br.com.technocorp.dao.LinhaDAO;
import br.com.technocorp.form.CoordinateForm;
import br.com.technocorp.form.IntinerarioForm;
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

                Response<ResponseBody> response = api.doBoleto("il", linha.getIdLinha()).execute();

                InputStream is = response.body().byteStream();

                try (InputStreamReader streamReader = new InputStreamReader(is)) {
                    BufferedReader reader = new BufferedReader(streamReader);

                    JSONParser jsonParser = new JSONParser();
                    JSONObject obj = (JSONObject) jsonParser.parse(reader);
                    Set<Map.Entry<String, JsonElement>> set = obj.entrySet();
                    //   System.out.println(set);
                    String line = null;


                    for (Object je : set) {

                        Map.Entry<String, JSONObject> hashMap = (Map.Entry<String, JSONObject>) je;

                        String linha1 = null;
                        try {


                            System.out.println("go ->>>> linha nome = " + linha.getNome() + " id = " + linha.getIdLinha());
                            System.out.println("aqui ");


                            Coordinate c = new Coordinate();
                            c.setLinha(linha);
                            try {
                                c.setLat( hashMap.getValue().get("lat").toString());
                                c.setLng( hashMap.getValue().get("lng").toString());
                            } catch (ClassCastException e1) {
                                System.out.println(hashMap.getValue().get("lat"));
                                System.out.println("___________________________");
                                c.setLng(null);
                                c.setLng(null);
                            }
                            System.out.println(">>>> coordenada " + c.getLat());
                            coordinateDAO.save(c);




                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        }
                    }
                    reader.close();

                }

                //JsonReader j = new JsonReader(is  ,"UTF-8");

                //System.out.println(response.body());
                //System.out.println(response.body().toArray());
//            JSONParser jsonParser = new JSONParser();
//            JSONObject obj = (JSONObject) jsonParser.parse(response.body().to);
//            Set<Map.Entry<String, JsonElement>> set = obj.entrySet();
//
//            for (Object je : set) {
//
//                Map.Entry<String, JSONObject> hashMap = (Map.Entry<String, JSONObject>) je;
//
//                String linha = null;
//                try {
//                    linha = hashMap.getValue().toJSONString();
//                    System.out.println(linha);
//                } catch (ClassCastException e) {
//
//                }
//            }
                //System.out.println(response.body().iterator().hasNext());
//


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("FIM -- - - ");
    }

    public List<Coordinate> findAllWeb(String codigo) {


        List<Coordinate> listCoordinate;
        listCoordinate = new ArrayList<>();

        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.poatransporte.com.br/php/facades/process.php/")

                    .build();

            ApiIntinerario api = retrofit.create(ApiIntinerario.class);

            Response<ResponseBody> response = api.doBoleto("il", new Integer(codigo)).execute();

            InputStream is = response.body().byteStream();

            try (InputStreamReader streamReader = new InputStreamReader(is)) {
                BufferedReader reader = new BufferedReader(streamReader);

                JSONParser jsonParser = new JSONParser();
                JSONObject obj = (JSONObject) jsonParser.parse(reader);
                Set<Map.Entry<String, JsonElement>> set = obj.entrySet();
                System.out.println(set);
                String line = null;


                for (Object je : set) {

                    Map.Entry<String, JSONObject> hashMap = (Map.Entry<String, JSONObject>) je;

                    String linha = null;
                    try {
                        linha = hashMap.getValue().toJSONString();
                        Coordinate c = new Coordinate();
                        c.setLat(hashMap.getValue().get("lat").toString());
                        c.setLng(hashMap.getValue().get("lng").toString());

                        listCoordinate.add(c);
                    } catch (ClassCastException e) {

                    }
                }
                reader.close();

            }

            //JsonReader j = new JsonReader(is  ,"UTF-8");

            //System.out.println(response.body());
            //System.out.println(response.body().toArray());
//            JSONParser jsonParser = new JSONParser();
//            JSONObject obj = (JSONObject) jsonParser.parse(response.body().to);
//            Set<Map.Entry<String, JsonElement>> set = obj.entrySet();
//
//            for (Object je : set) {
//
//                Map.Entry<String, JSONObject> hashMap = (Map.Entry<String, JSONObject>) je;
//
//                String linha = null;
//                try {
//                    linha = hashMap.getValue().toJSONString();
//                    System.out.println(linha);
//                } catch (ClassCastException e) {
//
//                }
//            }
            //System.out.println(response.body().iterator().hasNext());
//


        } catch (Exception e) {
            e.printStackTrace();
        }


        return listCoordinate;
    }


    public void create(IntinerarioForm intinerarioForm) {
        Linha linha = new Linha();
        linha.setNome(intinerarioForm.getNome());
        linha.setCodigo(intinerarioForm.getCodigo());
        linha = linhaDAO.save(linha);
        List<CoordinateForm> listCoordinate = intinerarioForm.getListCoordinate();

        for (CoordinateForm c : listCoordinate
        ) {
            Coordinate coordinate = c.build();
            coordinate.setLinha(linha);
            coordinateDAO.save(coordinate);
        }
    }

    public void delete(IntinerarioForm intinerarioForm) {

        List<Coordinate> list = coordinateDAO.findByIDW(intinerarioForm.getIdLinha());

        if (list != null) {
            for (Coordinate c : list) {
                coordinateDAO.delete(c);
            }
        }

        Linha linha = linhaDAO.findById(intinerarioForm.getIdMysql()).orElse(null);
        if (linha != null) {
            linhaDAO.delete(linha);
        }

    }

}
