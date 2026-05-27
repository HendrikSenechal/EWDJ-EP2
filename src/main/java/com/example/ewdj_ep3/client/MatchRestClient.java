package com.example.ewdj_ep3.client;

import com.example.ewdj_ep3.dto.request.InputMatchDTO;
import com.example.ewdj_ep3.dto.request.UpdateMatchDTO;
import com.example.ewdj_ep3.dto.response.MatchCapacityDTO;
import com.example.ewdj_ep3.dto.response.MatchResponseDTO;
import com.example.ewdj_ep3.utils.DateTimeFormats;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MatchRestClient {

    private final String SERVER_URI = "http://localhost:8080/api/matches";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(SERVER_URI)
            .build();

    public Flux<MatchResponseDTO> getAllMatches() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(MatchResponseDTO.class);
    }

    public Mono<MatchCapacityDTO> getStadiumCapacityById(Long id) {

        return webClient.get()
                .uri("/{id}/capacity", id)
                .retrieve()
                .bodyToMono(MatchCapacityDTO.class);
    }

    public Mono<MatchResponseDTO> getMatch(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(MatchResponseDTO.class);
    }

    public Mono<MatchResponseDTO> createMatch(InputMatchDTO dto) {
        return webClient.post()
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(MatchResponseDTO.class);
    }

    public Mono<MatchResponseDTO> updateMatch(Long id, UpdateMatchDTO dto) {
        return webClient.put()
                .uri("/{id}", id)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(MatchResponseDTO.class);
    }

    public Mono<MatchResponseDTO> deleteMatch(Long id) {
        return webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(MatchResponseDTO.class);
    }
}