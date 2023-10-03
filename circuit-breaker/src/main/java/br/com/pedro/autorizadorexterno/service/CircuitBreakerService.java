package br.com.pedro.autorizadorexterno.service;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.IntervalFunction;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CircuitBreakerService {
    // Um mapa para armazenar os circuit breakers por URL
    public static Map<String, CircuitBreaker> circuitBreakerMap = new HashMap<>();

    // Um método para obter ou criar um circuit breaker para uma URL
    public static CircuitBreaker getCircuitBreaker(String url) {
        // Verifica se o mapa contém um circuit breaker para a URL
        if (circuitBreakerMap.containsKey(url)) {
            // Retorna o circuit breaker existente
            return circuitBreakerMap.get(url);
        } else {
            // Cria um novo circuit breaker com a configuração padrão ou personalizada
            CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                    .failureRateThreshold(20)
                    .minimumNumberOfCalls(10)
                    .slidingWindowSize(10)
                    .waitIntervalFunctionInOpenState(IntervalFunction.of(5000L))
                    .build();
            CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(config);
            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(url);
            // Adiciona o circuit breaker ao mapa
            circuitBreakerMap.put(url, circuitBreaker);
            log.info(String.format("Circuit breaker para url %s criado", url));
            // Retorna o circuit breaker criado
            return circuitBreaker;
        }
    }

}
