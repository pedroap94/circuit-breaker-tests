package br.com.pedro.autorizadorexterno.controller;

import br.com.pedro.autorizadorexterno.model.EntryContract;
import br.com.pedro.autorizadorexterno.service.AutExtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("autorizar")
@AllArgsConstructor
public class AutExtController {
    private AutExtService autExtService;

    @PostMapping
    public ResponseEntity<Void> entryMessage(@RequestBody EntryContract entryMessage) {
        autExtService.gerarEventoAutExt(entryMessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
