package com.example.ewdj_ep3.client;

import com.example.ewdj_ep3.domain.match.MatchRestClient;
import com.example.ewdj_ep3.dto.request.InputMatchDTO;
import com.example.ewdj_ep3.dto.request.UpdateMatchDTO;
import com.example.ewdj_ep3.dto.response.MatchCapacityDTO;
import com.example.ewdj_ep3.dto.response.MatchResponseDTO;
import com.example.ewdj_ep3.enums.Outcome;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class RestClientDemo {

    private final MatchRestClient client = new MatchRestClient();

    public RestClientDemo() {

        System.out.println("\n------- GET ALL MATCHES -------");
        client.getAllMatches()
                .doOnNext(this::printMatchData)
                .blockLast();

        System.out.println("\n------- GET MATCH 73 -------");
        client.getMatch(73L)
                .doOnNext(this::printMatchData)
                .block();

        System.out.println("\n------- GET MATCH 73 CAPACITY -------");
        client.getStadiumCapacityById(73L)
                .doOnNext(this::printCapacityData)
                .block();

        System.out.println("\n------- CREATE MATCH -------");
        InputMatchDTO input = new InputMatchDTO(
                1L,
                2L,
                LocalDateTime.of(2026, 6, 14, 20, 0),
                "Lusail Stadium",
                88000,
                "A"
        );

        MatchResponseDTO createdMatch = client.createMatch(input)
                .doOnNext(this::printMatchData)
                .block();

        Long createdMatchId = createdMatch.id();

        System.out.println("\n------- UPDATE CREATED MATCH -------");
        UpdateMatchDTO update = new UpdateMatchDTO(
                1L,
                2L,
                LocalDateTime.of(2026, 6, 14, 21, 30),
                "Lusail Iconic Stadium",
                90000,
                "B",
                2,
                1,
                Outcome.HOME_VICTORY
        );

        client.updateMatch(createdMatchId, update)
                .doOnNext(this::printMatchData)
                .block();

        System.out.println("\n------- DELETE CREATED MATCH -------");
        client.deleteMatch(createdMatchId)
                .doOnNext(this::printMatchData)
                .block();

        System.out.println("\n------- GET NON-EXISTING MATCH -------");
        client.getMatch(99999L)
                .doOnNext(this::printMatchData)
                .doOnError(e -> System.out.println(e.getMessage()))
                .onErrorResume(e -> Mono.empty())
                .block();
    }

    private void printMatchData(MatchResponseDTO match) {
        System.out.printf(
                "ID=%s, %s vs %s, DateTime=%s, Stadium=%s, Capacity=%s, Score=%s-%s, Outcome=%s%n",
                match.id(),
                match.homeTeamName(),
                match.awayTeamName(),
                match.matchDateTime(),
                match.stadium(),
                match.stadiumCapacity(),
                match.scoreHomeTeam(),
                match.scoreAwayTeam(),
                match.outcome()
        );
    }

    private void printCapacityData(MatchCapacityDTO capacity) {
        System.out.println(capacity);
    }
}