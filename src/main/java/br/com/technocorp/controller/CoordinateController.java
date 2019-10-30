package br.com.technocorp.controller;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.CoordinateForm;

import br.com.technocorp.bean.Linha;
import br.com.technocorp.dao.CoordinateDAO;
import br.com.technocorp.dao.LinhaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coordinate")
public class CoordinateController {
    @Autowired
    private CoordinateDAO coordinateoDAO;

    @Autowired
    private LinhaDAO intinerarioDAO;

    @RequestMapping(value="doCad", method= RequestMethod.POST,consumes="application/json",produces="application/json")
    @ResponseBody
    public ResponseEntity update(@RequestBody CoordinateForm[] list) {

        for (CoordinateForm c : list) {
            Linha i = intinerarioDAO.findByIDW(c.getIdInt());
            Coordinate coordinate  =c.build();
            coordinate.setLinha(i);
            coordinateoDAO.save(coordinate);

        }
        // TODO: call persistence layer to update
        return new ResponseEntity( HttpStatus.OK);
    }
}
