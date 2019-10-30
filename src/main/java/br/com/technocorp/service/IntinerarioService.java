package br.com.technocorp.service;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.CoordinateJson;
import br.com.technocorp.bean.IntinerarioWrapper;
import br.com.technocorp.bean.Linha;
import br.com.technocorp.dao.CoordinateDAO;
import br.com.technocorp.dao.LinhaDAO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
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

    public CoordinateJson findAllWeb(String codigo) {
        List<CoordinateJson> listCoordinate = new ArrayList<CoordinateJson>();
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://www.poatransporte.com.br/php/facades/process.php?a=il&p=" + codigo);
        Response response = target.request().get();

        String responseAsString = response.readEntity(String.class);



        try {
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/coordinate.json");
            writer.write(responseAsString);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        CoordinateJson coordinate = new CoordinateJson();
        coordinate.setCodigo(codigo);

        Linha linhaIntinerario = linhaDAO.findByIDW(new Integer(codigo));

        try (Reader reader = new FileReader(System.getProperty("user.dir") + "/coordinate.json")) {


            JSONParser jsonParser = new JSONParser();
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            Set<Map.Entry<String, JsonElement>> set = obj.entrySet();
            int cnt = 0;
            for (Object je : set) {

                Map.Entry<String, JSONObject> hashMap = (Map.Entry<String, JSONObject>) je;

                String linha = null;
                try {
                    linha = hashMap.getValue().toJSONString();

                } catch (ClassCastException e) {

                }
                if (linha != null) {
                    String strAux[] = linha.split("\"");

                    Coordinate c = new Coordinate();
                    c.setLat(new Double(strAux[7]));
                    c.setLng(new Double(strAux[3]));

                    if (coordinateDAO.findLat(c.getLat(), c.getLng()) == null) {
                        coordinate.setCodigo(linhaIntinerario.getCodigo());
                        c.setLinha(linhaIntinerario);
                        c.setIdLinha(new Integer(codigo));

                        if (coordinate.getListCoordinate() == null) {
                            List<Coordinate> listC = new ArrayList<Coordinate>();
                            coordinate.setListCoordinate(listC);
                            coordinate.getListCoordinate().add(c);
                        } else {
                            coordinate.getListCoordinate().add(c);
                        }
                        coordinateDAO.save(c);
                    } else {
                        for (Coordinate coor : coordinateDAO.findAll()
                        ) {
                            if (coordinate.getListCoordinate() == null) {
                                List<Coordinate> list = new ArrayList<Coordinate>();
                                list.add(coor);
                                coordinate.setListCoordinate(list);
                            } else {
                                coordinate.getListCoordinate().add(coor);
                            }
                        }

                    }

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        response.close();
        return coordinate;
    }

}
