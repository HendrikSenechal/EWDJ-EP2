package com.example.ewdj_ep3.mapper;

import com.example.ewdj_ep3.domain.match.Match;
import com.example.ewdj_ep3.domain.team.Team;
import com.example.ewdj_ep3.dto.request.InputMatchDTO;
import com.example.ewdj_ep3.dto.request.UpdateMatchDTO;
import com.example.ewdj_ep3.dto.response.MatchResponseDTO;
import com.example.ewdj_ep3.enums.Outcome;

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

    public static Match toEntity(
            InputMatchDTO dto,
            Team homeTeam,
            Team awayTeam
    ) {

        return Match.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .matchDateTime(dto.matchDateTime())
                .stadium(dto.stadium())
                .stadiumCapacity(dto.stadiumCapacity())
                .worldCupGroup(dto.worldCupGroup())

                .scoreHomeTeam(0)
                .scoreAwayTeam(0)
                .outcome(Outcome.SCHEDULED)

                .build();
    }

    public static Match updateEntity(
            Match existingMatch,
            UpdateMatchDTO dto,
            Team homeTeam,
            Team awayTeam
    ) {

        existingMatch.setHomeTeam(homeTeam);

        existingMatch.setAwayTeam(awayTeam);

        existingMatch.setMatchDateTime(dto.matchDateTime());

        existingMatch.setStadium(dto.stadium());

        existingMatch.setStadiumCapacity(dto.stadiumCapacity());

        existingMatch.setWorldCupGroup(dto.worldCupGroup());

        existingMatch.setScoreHomeTeam(dto.scoreHomeTeam());

        existingMatch.setScoreAwayTeam(dto.scoreAwayTeam());

        existingMatch.setOutcome(dto.outcome());

        return existingMatch;
    }
}