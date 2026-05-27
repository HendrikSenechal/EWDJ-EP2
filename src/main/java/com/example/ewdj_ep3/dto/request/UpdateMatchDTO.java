package com.example.ewdj_ep3.dto.request;

import com.example.ewdj_ep3.enums.Outcome;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateMatchDTO(

        @NotNull(message = "homeTeamId is required")
        Long homeTeamId,

        @NotNull(message = "awayTeamId is required")
        Long awayTeamId,

        @NotNull(message = "matchDateTime is required")
        java.time.LocalDateTime matchDateTime,

        @NotNull(message = "stadium is required")
        String stadium,

        @NotNull(message = "stadiumCapacity is required")
        @Min(1)
        Integer stadiumCapacity,

        @NotNull(message = "worldCupGroup is required")
        String worldCupGroup,

        @NotNull(message = "scoreHomeTeam is required")
        @Min(0)
        Integer scoreHomeTeam,

        @NotNull(message = "scoreAwayTeam is required")
        @Min(0)
        Integer scoreAwayTeam,

        @NotNull(message = "outcome is required")
        Outcome outcome
) {
    public UpdateMatchDTO() {
        this(null, null, null, null, null, null, null, null, null);
    }
}