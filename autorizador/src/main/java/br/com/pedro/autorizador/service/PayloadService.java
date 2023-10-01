package br.com.pedro.autorizador.service;

import br.com.pedro.autorizador.model.Payload;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@AllArgsConstructor
public class PayloadService {

    private final RestTemplate restTemplate;

    public void sendPayloadFinancialA() {
            sendPayload("K7Bank", 10L);
    }

    public void sendPayloadFinancialB() {
        sendPayload("D4Bank", 12L);
    }

    private void sendPayload(String nomeAut, Long id) {
        try{
            ResponseEntity<Payload> zeBank = restTemplate.postForEntity("http://localhost:9090/autorizar",
                    Payload.builder().id(id).nomeAutorizar(nomeAut).build(),
                    Payload.class
            );
            if (zeBank.getStatusCode().is2xxSuccessful()) {
                log.info(String.format("Envio bem sucedido para %s", nomeAut));
                return;
            }
            log.error(String.format("Falha ao enviar requisição para %s", nomeAut));
        } catch (Exception e) {
            log.error("Falha ao realizar conexão");
        }

    }
}
