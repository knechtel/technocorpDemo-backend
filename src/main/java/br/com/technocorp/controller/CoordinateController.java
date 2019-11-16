package br.com.technocorp.controller;

import br.com.technocorp.bean.CoordinateJson;
import br.com.technocorp.dto.IntinerarioDTO;
import br.com.technocorp.form.IntinerarioCoordinateForm;
import br.com.technocorp.form.IntinerarioForm;
import br.com.technocorp.service.IntinerarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public CoordinateJson list(@RequestBody IntinerarioCoordinateForm form) {
        CoordinateJson cj = intinerarioService.findAllWeb(form.getCodigo());
        return cj;
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
}
