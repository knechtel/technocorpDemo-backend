package uti;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.CoordinateDTO;
import br.com.technocorp.bean.CoordinateJson;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import javax.ws.rs.core.Response;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class TestCoordinate {

    public static void main(String[] args) {
//        List<CoordinateJson> listCoordinate = new ArrayList<CoordinateJson>();
//        ResteasyClient client = new ResteasyClientBuilder().build();
//        ResteasyWebTarget target = client.target("http://www.poatransporte.com.br/php/facades/process.php?a=il&p=5565" );
//        Response response = target.request().get();
//
//        String responseAsString = response.readEntity(String.class);
//        System.out.println(responseAsString);

        Gson gson = new Gson();
//        try {
//            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/coordinate.json");
//            writer.write(responseAsString);
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        CoordinateJson coordinate = null;
        Gson gsonReader = new Gson();

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
                    System.out.println(linha);
                    cnt++;
                } catch (ClassCastException e) {

                }
                if (linha != null) {
                    String strAux[] = linha.split("\"");
                    System.out.println("lng ->"+strAux[3]);
                    System.out.println("lat ->"+strAux[7]);

                    Coordinate c = new Coordinate();

                    c.setLat(new Double(strAux[7]));
                    c.setLng(new Double(strAux[3]));
                }

            }

            System.out.println(cnt);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //     response.close();
    }
}
