package br.com.technocorp.controller;

import br.com.technocorp.bean.Linha;
import br.com.technocorp.bean.IntinerarioForm;
import br.com.technocorp.dao.LinhaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/linha")
public class LinhaController {

    @Autowired
    private LinhaDAO intinerarioDAO;



    @RequestMapping(value = "doCad", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Iterable<Linha> doCad(@Valid @RequestBody IntinerarioForm autorForm) {

        intinerarioDAO.save(autorForm.build());

        //forçando o retorno ser a lista só para não ficar preso no lance dos redirect e cors do navegador
        return lista();
    }


    @RequestMapping(value="salvar",consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Iterable<Linha> salva(@Valid @RequestBody IntinerarioForm autorForm) {
//        for (Intinerario i : autorForm.getListIntinarario()) {
         intinerarioDAO.save(autorForm.build());
//        }
        //forçando o retorno ser a lista só para não ficar preso no lance dos redirect e cors do navegador
        return lista();
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Linha> lista() {
        return intinerarioDAO.findAll();
    }

    @RequestMapping(value = "lista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Linha> findAll() {
        return intinerarioDAO.findAll();
    }

    @RequestMapping(value="doList", method=RequestMethod.POST,consumes="application/json",produces="application/json")
    @ResponseBody
    public ResponseEntity update(@RequestBody IntinerarioForm[] list) {

        for (IntinerarioForm i : list) {
            if (intinerarioDAO.findByIDW(i.build().getId()) == null) {
                intinerarioDAO.save(i.build());
            }
        }
        // TODO: call persistence layer to update
        return new ResponseEntity( HttpStatus.OK);
    }

    @RequestMapping(value = "/doCreate", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody IntinerarioForm intinerarioForm) {

        if (intinerarioDAO.findByCode(intinerarioForm.build().getCodigo()) == null) {
            intinerarioDAO.save(intinerarioForm.build());
        }

        // TODO: call persistence layer to update
        return new ResponseEntity(HttpStatus.OK);
    }

}
