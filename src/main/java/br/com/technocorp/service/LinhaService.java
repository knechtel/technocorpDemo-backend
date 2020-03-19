package br.com.technocorp.service;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.Linha;
import br.com.technocorp.dao.CoordinateDAO;
import br.com.technocorp.dao.LinhaDAO;
import br.com.technocorp.form.CoordinateForm;
import br.com.technocorp.form.IntinerarioForm;
import br.com.technocorp.form.LinhaForm;
import br.com.technocorp.form.LinhaFormView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.ApiIntinerario;
import util.DistanceCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LinhaService {

    @Autowired
    private LinhaDAO linhaDAO;
    @Autowired
    private CoordinateDAO coordinateDAO;


    public List<LinhaFormView> findAllWeb() {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.poatransporte.com.br/php/facades/process.php/")

                    .build();

            ApiIntinerario api = retrofit.create(ApiIntinerario.class);

            Response<ResponseBody> response = api.getAllLinhas("nc", "o").execute();

            InputStream is = response.body().byteStream();

            try (InputStreamReader streamReader = new InputStreamReader(is)) {
                BufferedReader reader = new BufferedReader(streamReader);


                Type listType = new TypeToken<ArrayList<LinhaFormView>>() {
                }.getType();
                List<LinhaFormView> listLinha = new Gson().fromJson(reader, listType);
                for (LinhaFormView l :
                        listLinha) {
                    Linha linha = l.toLinha(l);
                    if (linhaDAO.findByCode(linha.getCodigo(), linha.getNome()) == null) {
                        linhaDAO.save(linha);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        List<LinhaFormView> linhaFormView = new ArrayList<>();
        for (Linha l : linhaDAO.findAll()) {
            linhaFormView.add(l.build(l));
        }
        return linhaFormView;
    }

    public LinhaForm read(LinhaForm linhaForm) {
        LinhaForm linhaDTO = new LinhaForm();
        Linha linha = linhaDAO.findById(linhaForm.getId()).orElse(null);
        if (linha != null) {
            linhaDTO.setId(linha.getId());
            linhaDTO.setNome(linha.getNome());
            linhaDTO.setCodigo(linha.getCodigo());
            List<CoordinateForm> listCoordinateForm = new ArrayList<>();
            for (Coordinate c : linha.getListCoordinate()) {
                CoordinateForm form = new CoordinateForm();
                form.setLat(c.getLat());
                form.setLng(c.getLng());
                listCoordinateForm.add(form);
            }
            linhaDTO.setListCoordenada(listCoordinateForm);
        }


        return linhaDTO;
    }


    public Linha createLinha(LinhaForm linhaForm) {
        //se existir linha nao deixa cadastrar
        Linha linha = null;
        List<Linha> linhaMesmoNome = linhaDAO.findName(linhaForm.getNome());

        if (linhaMesmoNome != null && linhaMesmoNome.size() >= 1) {
            return null;
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
            coordinateDAO.save(c);
        }
        linhaDAO.delete(linhaDAO.findById(linhaForm.getId()).orElse(null));

    }

    public void update(Linha linhaForm) {
        Linha linhaAux = linhaDAO.findById(linhaForm.getId()).orElse(null);
        for (Coordinate c1 : linhaAux.getListCoordinate()) {
            if (coordinateDAO.findById(c1.getId()) != null)
                coordinateDAO.deleteById(c1.getId());
        }
        linhaAux.setListCoordinate(linhaForm.getListCoordinate());
        for (Coordinate c : linhaAux.getListCoordinate()) {
            c.setLinha(linhaAux);
            coordinateDAO.save(c);
        }
        linhaDAO.save(linhaAux);
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
            if (c.getLng() != null && c.getLng() != null) {
                if (DistanceCalculator.distance(new Double(c.getLat()), new Double(c.getLng()), lat, lng, "K")
                        <= raioKm) {

                    if (!listLinha.contains(new LinhaFormView().build(c.getLinha()))) {
                        listLinha.add(new LinhaFormView().build(c.getLinha()));
                    }
                }
            }
        }

        return listLinha;
    }

    public List<LinhaFormView> findLinhaByName(LinhaFormView form) {
        List<LinhaFormView> listForm = new ArrayList<>();
        for (Linha linha :
                linhaDAO.findByName(form.getNome())) {
            LinhaFormView lfv = new LinhaFormView();
            lfv.setCodigo(linha.getCodigo());
            lfv.setId(linha.getId());
            lfv.setNome(linha.getNome());
            listForm.add(lfv);
        }
        return listForm;
    }
}
