package br.com.technocorp.controller;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.Linha;
import br.com.technocorp.form.CoordinateForm;
import br.com.technocorp.form.LinhaForm;
import br.com.technocorp.dao.LinhaDAO;
import br.com.technocorp.form.LinhaFormView;
import br.com.technocorp.service.IntinerarioService;
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

    @Autowired
    private IntinerarioService intinerarioService;

    @Autowired
    private LinhaService linhaService;

    @RequestMapping(value="create",consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity create(@Valid @RequestBody LinhaForm linhaForm) {
        linhaForm.setAlreadInDatabase(false);
        linhaService.createLinha(linhaForm);

        if(linhaForm.isAlreadInDatabase()){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    //delete linha
    @RequestMapping(value="delete",consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public LinhaForm delete(@Valid @RequestBody LinhaForm linhaForm){
        linhaService.delete(linhaForm);
        return linhaForm;
    }


    @RequestMapping(value = "lista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<LinhaFormView> findAll() {
        //colocar isso em classe de serviço
        for (Linha linha:linhaService.findAllWeb()
             ) {
            if (linhaDAO.findByCode(linha.getCodigo(),linha.getNome()) ==null) {
                linha.setIdLinha(linha.getId());
                linhaDAO.save(linha);
            }

        }
        return linhaService.findAll();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Linha update(@RequestBody LinhaForm linhaForm) {
        Linha linha = linhaForm.build();
        linha.setListCoordinate(linhaForm.buildList());
        linhaService.update(linha);
        return linha;
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.POST)
    public List<Linha> findByName(@RequestBody LinhaForm linhaForm) {
        return linhaDAO.findByName(linhaForm.getNome());
    }

    @RequestMapping(value = "getLinhas", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity gettAllLinhas(@RequestBody CoordinateForm form){

        return new ResponseEntity(HttpStatus.OK);
    }

}
