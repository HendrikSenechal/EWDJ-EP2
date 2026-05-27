package com.example.ewdj_ep3.dto.response;

import com.example.ewdj_ep3.enums.Outcome;

import java.time.LocalDateTime;

public record MatchResponseDTO(

        Long id,

        Long homeTeamId,
        String homeTeamName,
        String homeTeamFlag,

        Long awayTeamId,
        String awayTeamName,
        String awayTeamFlag,

        LocalDateTime matchDateTime,

        String stadium,

        int stadiumCapacity,

        String worldCupGroup,

        int scoreHomeTeam,

        int scoreAwayTeam,

        Outcome outcome

) {
}