package br.com.pedro.autorizadorexterno.service;

import br.com.pedro.autorizadorexterno.model.EntryMessage;
import br.com.pedro.autorizadorexterno.model.ExitMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AutExtService {
    final ModelMapper modelMapper;
    private SenderService sender;

    //TODO adicionar o circuit breaker aqui
    public void gerarEventoAutExt(EntryMessage entryMessage) {

            ExitMessage exitMessage = mountExitContract(entryMessage);
        try {
            sender.enviarEventoAutExtWithCircuitBreaker(exitMessage);
            log.info(String.format("Estado do circuit breaker para %s: %s",
                    entryMessage.getNomeAutorizar(),
                    CircuitBreakerService.getCircuitBreaker(exitMessage.getUrl()).getState()));
            log.info(String.valueOf(CircuitBreakerService.getCircuitBreaker(exitMessage.getUrl()).getMetrics().getNumberOfFailedCalls()));
            log.info(String.valueOf(CircuitBreakerService.getCircuitBreaker(exitMessage.getUrl()).getMetrics().getNumberOfSuccessfulCalls()));
        } catch (Exception e) {
            log.error(String.format("Estado do circuit breaker para %s: %s",
                    entryMessage.getNomeAutorizar(),
                    CircuitBreakerService.getCircuitBreaker(exitMessage.getUrl()).getState()));
        }

    }

    private ExitMessage mountExitContract(EntryMessage entryMessage) {
        var exitMessage = modelMapper.map(entryMessage, ExitMessage.class);
        if (exitMessage.getId() <= 10) {
            exitMessage.setUrl("A");
            return exitMessage;
        }
        exitMessage.setUrl("B");
        return exitMessage;
    }
}
