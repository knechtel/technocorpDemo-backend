package br.com.technocorp.controller;

import br.com.technocorp.bean.Linha;
import br.com.technocorp.bean.IntinerarioForm;
import br.com.technocorp.dao.LinhaDAO;
import br.com.technocorp.service.LinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/linha")
public class LinhaController {

    @Autowired
    private LinhaDAO linhaDAO;


    @RequestMapping(value="salvar",consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Iterable<Linha> salva(@Valid @RequestBody IntinerarioForm autorForm) {
        linhaDAO.save(autorForm.build());
        return findAll();
    }



    @RequestMapping(value = "lista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Linha> findAll() {
        LinhaService linhaService = new LinhaService();

        for (Linha linha:linhaService.findAllWeb()
             ) {
            if (linhaDAO.findByIDW(linha.getId()) == null) {
                linhaDAO.save(linha);
            }

        }
        return linhaDAO.findAll();
    }

    @RequestMapping(value="doPersistList", method=RequestMethod.POST,consumes="application/json",produces="application/json")
    @ResponseBody
    public ResponseEntity doPersistList(@RequestBody IntinerarioForm[] list) {

        for (IntinerarioForm i : list) {
            if (linhaDAO.findByIDW(i.build().getId()) == null) {
                linhaDAO.save(i.build());
            }
        }

        return new ResponseEntity( HttpStatus.OK);
    }

    @RequestMapping(value = "/doCreate", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody IntinerarioForm intinerarioForm) {

        if (linhaDAO.findByCode(intinerarioForm.build().getCodigo()) == null) {
            linhaDAO.save(intinerarioForm.build());
        }


        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.POST)
    public List<Linha> findByName(@RequestBody IntinerarioForm intinerarioForm) {




        return linhaDAO.findByName(intinerarioForm.getNome());
    }


}
