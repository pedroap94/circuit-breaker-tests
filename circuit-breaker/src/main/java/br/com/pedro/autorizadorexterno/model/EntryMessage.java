package br.com.pedro.autorizadorexterno.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class EntryMessage {
    private Long id;
    private String nomeAutorizar;
}
