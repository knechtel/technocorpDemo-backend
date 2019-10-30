package br.com.technocorp.controller;

import br.com.technocorp.bean.*;

import br.com.technocorp.dao.CoordinateDAO;
import br.com.technocorp.dao.LinhaDAO;
import br.com.technocorp.service.IntinerarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordinate")
public class CoordinateController {


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


    @RequestMapping(value="list", method= RequestMethod.POST,consumes="application/json",produces="application/json")
    @ResponseBody
    public CoordinateJson list(@RequestBody IntinerarioCoordinateForm form) {

         CoordinateJson list= intinerarioService.findAllWeb(form.getCodigo());
        // TODO: call persistence layer to update
        return list;
    }
}
