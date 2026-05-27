package com.example.ewdj_ep3.mapper;

import com.example.ewdj_ep3.domain.match.Match;
import com.example.ewdj_ep3.dto.response.MatchResponseDTO;

public class MatchMapper {

    public static MatchResponseDTO toDTO(Match match) {

        return new MatchResponseDTO(

                match.getId(),

                match.getHomeTeam().getId(),
                match.getHomeTeam().getName(),
                match.getHomeTeam().getFlag(),

                match.getAwayTeam().getId(),
                match.getAwayTeam().getName(),
                match.getAwayTeam().getFlag(),

                match.getMatchDateTime(),

                match.getStadium(),

                match.getStadiumCapacity(),

                match.getWorldCupGroup(),

                match.getScoreHomeTeam(),

                match.getScoreAwayTeam(),

                match.getOutcome()
        );
    }
}