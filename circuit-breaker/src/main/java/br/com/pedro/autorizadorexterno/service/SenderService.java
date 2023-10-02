package br.com.pedro.autorizadorexterno.service;

import br.com.pedro.autorizadorexterno.model.ExitMessage;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class SenderService {
    private final RestTemplate restTemplate;
    private final String URL_SAIDA = "http://localhost:1010/autorizacao-externa";

    public void enviarEventoAutExt(ExitMessage exitMessage) {
        String url = String.format("%s/%s", URL_SAIDA, exitMessage.getUrl());
        try {
            var exitMessageResponseEntity = restTemplate.postForEntity(url, exitMessage, ExitMessage.class);
            if (exitMessageResponseEntity.getStatusCode().is2xxSuccessful()) {
                log.info(String.format("Mensagem enviada para autorizador externo %s", exitMessage.getNomeAutorizar()));
            }
        } catch (Exception e) {
            log.error("Falha de conexão");
        }
    }

    public void enviarEventoAutExtWithCircuitBreaker(ExitMessage exitMessage) {
        String url = String.format("%s/%s", URL_SAIDA, exitMessage.getUrl());
        CircuitBreaker circuitBreaker = CircuitBreakerService.getCircuitBreaker(url);
        Try<String> result = Try.ofSupplier(CircuitBreaker.decorateSupplier(circuitBreaker,
                () -> restTemplate.postForEntity(url, exitMessage, String.class).getBody()));
        CircuitBreakerService.circuitBreakerMap.put(exitMessage.getUrl(), circuitBreaker);
        if (result.isSuccess()) {
            log.info(String.format("Mensagem enviada para autorizador externo %s", exitMessage.getNomeAutorizar()));
            return;
        }

        if (circuitBreaker.getState().toString().equals("OPEN")){
            fallbackEnvioMensagem(exitMessage);
        }

        log.error("Falha de conexão");
    }

    private String fallbackEnvioMensagem(ExitMessage exitMessage) {
        log.info(String.format("Fallback ativo para emissor %s. Enviando dados para barramento",
                exitMessage.getNomeAutorizar()));
        return null;
    }

}
