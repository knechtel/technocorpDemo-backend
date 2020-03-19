package br.com.technocorp.controller;

import br.com.technocorp.bean.Coordinate;
import br.com.technocorp.bean.Linha;
import br.com.technocorp.form.CoordinateForm;
import br.com.technocorp.form.IntinerarioCoordinateForm;
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

        if(linhaService.createLinha(linhaForm)==null){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="read",consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public LinhaForm read(@RequestBody LinhaForm linhaForm) {
        return  linhaService.read(linhaForm);

    }

    @RequestMapping(value="delete",consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public LinhaForm delete(@Valid @RequestBody LinhaForm linhaForm){
        linhaService.delete(linhaForm);
        return linhaForm;
    }


    @RequestMapping(value = "lista", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LinhaFormView> findAll() {
        return linhaService.findAllWeb();
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public  ResponseEntity update(@RequestBody LinhaForm linhaForm) {
        Linha linha = linhaForm.build();
        linha.setListCoordinate(linhaForm.buildList());
        linhaService.update(linha);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.POST)
    public List<LinhaFormView> findByName(@RequestBody LinhaFormView linhaForm) {
        return linhaService.findLinhaByName(linhaForm);
    }

    @RequestMapping(value = "linhasByRaioEmKm", method = RequestMethod.POST)
    @ResponseBody
    public List<LinhaFormView> gettAllLinhas(@RequestBody CoordinateForm form){

        return linhaService.getAllLinhasByDistance(new Double(form.getLat().trim()),new Double(form.getLng().trim()),form.getRaiokm());
    }

    @RequestMapping(value = "listaIntinerario", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<Coordinate> listaIntinerario(@RequestBody IntinerarioCoordinateForm form) {
        return intinerarioService.findAllIntinerario(form);
    }

}
