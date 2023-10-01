package br.com.pedro.autorizador.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Payload {
    private Long id;
    private String nomeAutorizar;
}
