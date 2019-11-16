package br.com.technocorp.service;

import br.com.technocorp.bean.CoordinateJson;
import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.dto.CoordinateDTO;
import br.com.technocorp.dto.IntinerarioDTO;
import br.com.technocorp.bean.Linha;
import br.com.technocorp.dao.CoordinateDAO;
import br.com.technocorp.dao.LinhaDAO;
import br.com.technocorp.form.CoordinateForm;
import br.com.technocorp.form.IntinerarioForm;
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
        //response.close();
        return null;
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
