package uti;

import br.com.technocorp.bean.IntinerarioWrapper;
import br.com.technocorp.bean.Linha;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o");
        Response response = target.request().get();

        String responseAsString = response.readEntity(String.class);
        System.out.println(responseAsString);
        IntinerarioWrapper intinerarioWrapper=null;
        Gson gson = new Gson();
        try {
            FileWriter writer = new FileWriter(System.getProperty("user.dir")+"/linhas.json");
            writer.write(responseAsString);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gsonReader = new Gson();

        try (Reader reader = new FileReader(System.getProperty("user.dir")+"/linhas.json")) {

            Linha[] linhas = gsonReader.fromJson(reader, Linha[].class);
            System.out.println("AQUIII");
            System.out.println(linhas[0].getCodigo());

        } catch (IOException e) {
            e.printStackTrace();
        }
        response.close();
    }
}
