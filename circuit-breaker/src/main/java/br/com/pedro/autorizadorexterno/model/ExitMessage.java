package br.com.pedro.autorizadorexterno.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExitMessage extends EntryMessage {
    private String url;

    public ExitMessage(Long id, String nomeAutorizar, String url) {
        super(id, nomeAutorizar);
        this.url = url;
    }

    public ExitMessage() {
    }
}
