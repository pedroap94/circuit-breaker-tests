package br.com.pedro.autorizadorexterno.controller;

import br.com.pedro.autorizadorexterno.model.AutorizacaoExternaModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("autorizacao-externa")
public class AutorizacaoExternaController {

    @PostMapping("A")
    public ResponseEntity<Void> getAutorizacaoExternaA(@RequestBody AutorizacaoExternaModel autorizacaoExternaModel) {
        System.out.println(autorizacaoExternaModel.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("B")
    public ResponseEntity<Void> getAutorizacaoExternaB(@RequestBody AutorizacaoExternaModel autorizacaoExternaModel) {
        System.out.println(autorizacaoExternaModel.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
