package util;


import com.google.gson.JsonElement;

import com.google.gson.stream.JsonReader;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TestRetro {
    public static void main(String[] args) {
        try {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.poatransporte.com.br/php/facades/process.php/")

                .build();

            ApiIntinerario api = retrofit.create(ApiIntinerario.class);

            Response<ResponseBody> response = api.doBoleto("il",5566).execute();

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
                        System.out.println(">> mais um teste ->>"+linha);

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
    }
}





