package com.example.ewdj_ep3.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record InputMatchDTO(

        @NotNull(message = "{match.homeTeamId.notnull}")
        Long homeTeamId,

        @NotNull(message = "{match.awayTeamId.notnull}")
        Long awayTeamId,

        @NotNull(message = "{match.matchDateTime.notnull}")
        @FutureOrPresent(message = "{match.matchDateTime.future}")
        LocalDateTime matchDateTime,

        @NotBlank(message = "{match.stadium.notblank}")
        @Size(min = 2, max = 80, message = "{match.stadium.size}")
        String stadium,

        @NotNull(message = "{match.stadiumCapacity.notnull}")
        @Min(value = 1, message = "{match.stadiumCapacity.min}")
        Integer stadiumCapacity,

        @NotBlank(message = "{match.worldCupGroup.notblank}")
        @Size(min = 1, max = 10, message = "{match.worldCupGroup.size}")
        String worldCupGroup
) {
    public InputMatchDTO() {
        this(null, null, null, null, null, null);
    }
}