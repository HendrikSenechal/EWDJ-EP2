package com.example.ewdj_ep3.dto.request;

import com.example.ewdj_ep3.enums.Outcome;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateMatchDTO(

        @NotNull(message = "{match.homeTeamId.notnull}")
        Long homeTeamId,

        @NotNull(message = "{match.awayTeamId.notnull}")
        Long awayTeamId,

        @NotNull(message = "{match.matchDateTime.notnull}")
        java.time.LocalDateTime matchDateTime,

        @NotNull(message = "{match.stadium.notnull}")
        String stadium,

        @NotNull(message = "{match.stadiumCapacity.notnull}")
        @Min(value = 1, message = "{match.stadiumCapacity.min}")
        Integer stadiumCapacity,

        @NotNull(message = "{match.worldCupGroup.notnull}")
        String worldCupGroup,

        @NotNull(message = "{match.scoreHomeTeam.notnull}")
        @Min(value = 0, message = "{match.scoreHomeTeam.min}")
        Integer scoreHomeTeam,

        @NotNull(message = "{match.scoreAwayTeam.notnull}")
        @Min(value = 0, message = "{match.scoreAwayTeam.min}")
        Integer scoreAwayTeam,

        @NotNull(message = "{match.outcome.notnull}")
        Outcome outcome
) {
    public UpdateMatchDTO() {
        this(null, null, null, null, null, null, null, null, null);
    }
}