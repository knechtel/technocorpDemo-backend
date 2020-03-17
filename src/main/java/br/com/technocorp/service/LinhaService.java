package br.com.technocorp.service;


import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.Linha;
import br.com.technocorp.dao.CoordinateDAO;
import br.com.technocorp.dao.LinhaDAO;
import br.com.technocorp.form.LinhaForm;
import br.com.technocorp.form.LinhaFormView;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.objects.XNull;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.DistanceCalculator;

import javax.ws.rs.core.Response;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LinhaService {

    @Autowired
    private LinhaDAO linhaDAO;
    @Autowired
    private CoordinateDAO coordinateDAO;

    public List<Linha> findAllWeb() {
        List<Linha> listLinha = new ArrayList<Linha>();
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o");
        Response response = target.request().get();

        String responseAsString = response.readEntity(String.class);

        Gson gson = new Gson();
        try {
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "/linhas.json");
            writer.write(responseAsString);
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gsonReader = new Gson();

        try (Reader reader = new FileReader(System.getProperty("user.dir") + "/linhas.json")) {

            Linha[] linhas = gsonReader.fromJson(reader, Linha[].class);
            System.out.println("AQUIII");
            for (Linha l : linhas
            ) {
                System.out.println(">>>>>>>");
                System.out.println(l.getCodigo());

                System.out.println("===========                 ");
                l.getIdLinha();
                System.out.println("meu deus !! linha");
                listLinha.add(l);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        response.close();
        return listLinha;
    }


    public Linha createLinha(LinhaForm linhaForm) {
        //se existir linha nao deixa cadastrar
        Linha linha = null;
        List<Linha> linhaMesmoNome = linhaDAO.findName(linhaForm.getNome());

        if (linhaMesmoNome != null && linhaMesmoNome.size() >= 1) {
            linhaForm.setAlreadInDatabase(true);
        } else {
            linha = linhaDAO.save(linhaForm.build());
            for (Coordinate c :
                    linhaForm.buildList()) {
                c.setLinha(linha);
                coordinateDAO.save(c);
            }
            linha.setListCoordinate(linhaForm.buildList());

        }
        return linha;
    }

    public void delete(LinhaForm linhaForm) {
        for (Coordinate c :
                coordinateDAO.findIdLinha(linhaForm.getId())) {
            c.setLinha(null);
            System.out.println(c);
            coordinateDAO.save(c);
        }
        linhaDAO.delete(linhaDAO.findById(linhaForm.getId()).orElse(null));

    }

    public Linha update(Linha linha) {
        return linhaDAO.save(linha);
    }


    public Iterable<LinhaFormView> findAll() {
        List<LinhaFormView> listLinha = new ArrayList<>();
        for (Linha l : linhaDAO.findAllLinha()
        ) {
            LinhaFormView lfv = new LinhaFormView();

            lfv.setCodigo(l.getCodigo());
            lfv.setNome(l.getNome());
            lfv.setId(l.getId());
            listLinha.add(lfv);
        }
        return listLinha;
    }


    public List<LinhaFormView> getAllLinhasByDistance(double lat, double lng, double raioKm) {
        List<LinhaFormView> listLinha = new ArrayList();
        for (Coordinate c :
                coordinateDAO.findAll()) {
            if (DistanceCalculator.distance(new Double(c.getLat()), new Double(c.getLng()), lat, lng, "K")
                    <= raioKm) {

                if (!listLinha.contains(new LinhaFormView().build(c.getLinha()))) {
                    listLinha.add(new LinhaFormView().build(c.getLinha()));
                }
            }

        }

        return listLinha;
    }

    public List<LinhaFormView>findLinhaByName(LinhaFormView form){
        List<LinhaFormView> listForm = new ArrayList<>();
        for (Linha linha:
                linhaDAO.findByName(form.getNome())) {
            LinhaFormView lfv = new LinhaFormView();
            lfv.setCodigo(linha.getCodigo());
            lfv.setId(linha.getId());
            lfv.setNome(linha.getNome());
            listForm.add(lfv);
        }
        return  listForm;
    }
}
