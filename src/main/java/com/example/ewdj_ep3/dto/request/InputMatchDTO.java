package com.example.ewdj_ep3.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record InputMatchDTO(

        @NotNull(message = "homeTeamId is required")
        Long homeTeamId,

        @NotNull(message = "awayTeamId is required")
        Long awayTeamId,

        @NotNull(message = "matchDateTime is required")
        @FutureOrPresent(message = "matchDateTime cannot be in the past")
        LocalDateTime matchDateTime,

        @NotBlank(message = "stadium is required")
        @Size(min = 2, max = 80)
        String stadium,

        @NotNull(message = "stadiumCapacity is required")
        @Min(value = 1)
        Integer stadiumCapacity,

        @NotBlank(message = "worldCupGroup is required")
        @Size(min = 1, max = 10)
        String worldCupGroup
) {
    public InputMatchDTO() {
        this(null, null, null, null, null, null);
    }
}