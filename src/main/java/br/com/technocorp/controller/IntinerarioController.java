package br.com.technocorp.controller;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.CoordinateJson;
import br.com.technocorp.dto.IntinerarioDTO;
import br.com.technocorp.form.CoordinateForm;
import br.com.technocorp.form.IntinerarioCoordinateForm;
import br.com.technocorp.form.IntinerarioForm;
import br.com.technocorp.service.IntinerarioService;
import com.google.gson.JsonElement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

@RestController
@RequestMapping("/api/intinerario")
public class IntinerarioController {


    @Autowired
    private IntinerarioService intinerarioService;

//    @RequestMapping(value="doCad", method= RequestMethod.POST,consumes="application/json",produces="application/json")
//    @ResponseBody
//    public ResponseEntity createList(@RequestBody CoordinateForm[] list) {
//
//        for (CoordinateForm c : list) {
//            Linha i = intinerarioDAO.findByIDW(c.getIdInt());
//            Coordinate coordinate  =c.build();
//            coordinate.setLinha(i);
//            coordinateoDAO.save(coordinate);
//
//        }
//        // TODO: call persistence layer to update
//        return new ResponseEntity( HttpStatus.OK);
//    }


    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<Coordinate> list(@RequestBody IntinerarioCoordinateForm form) {
        return intinerarioService.findAllWeb(form.getCodigo());
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity create(@RequestBody IntinerarioForm intinerarioDTO) {
        intinerarioService.create(intinerarioDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity delete(@RequestBody IntinerarioForm intinerarioDTO) {


        intinerarioService.delete(intinerarioDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity gettAllIntinerario(){
        intinerarioService.getAll();
        return new ResponseEntity(HttpStatus.OK);
    }


}
