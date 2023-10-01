package br.com.pedro.autorizadorexterno.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AutorizacaoExternaModel {
    private Long id;
    private String nomeAutorizar;
}
