package com.example.ewdj_ep3.dto.response;

import com.example.ewdj_ep3.enums.Outcome;
import com.example.ewdj_ep3.utils.LocalDateTimeDeserializer;
import com.example.ewdj_ep3.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public record MatchResponseDTO(

        Long id,

        Long homeTeamId,
        String homeTeamName,
        String homeTeamFlag,

        Long awayTeamId,
        String awayTeamName,
        String awayTeamFlag,

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime matchDateTime,

        String stadium,

        int stadiumCapacity,

        String worldCupGroup,

        int scoreHomeTeam,

        int scoreAwayTeam,

        Outcome outcome

) {
}