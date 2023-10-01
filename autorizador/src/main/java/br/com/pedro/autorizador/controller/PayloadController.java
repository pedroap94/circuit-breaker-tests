package br.com.pedro.autorizador.controller;

import br.com.pedro.autorizador.service.PayloadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("enviar-payload")
@AllArgsConstructor
public class PayloadController {

    private PayloadService payloadService;

    @GetMapping("a")
    public ResponseEntity<Void> enviarPayloadA(){
        payloadService.sendPayloadFinancialA();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("b")
    public ResponseEntity<Void> enviarPayloadB(){
        payloadService.sendPayloadFinancialB();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
