package br.com.technocorp.controller;


import br.com.technocorp.service.IntinerarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/intinerario")
public class IntinerarioController {


    @Autowired
    private IntinerarioService intinerarioService;




    @RequestMapping(value = "getAll", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity gettAllIntinerario(){
        intinerarioService.getAll();
        return new ResponseEntity(HttpStatus.OK);
    }


}
