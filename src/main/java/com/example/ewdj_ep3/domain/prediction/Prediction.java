package com.example.ewdj_ep3.domain.prediction;

import com.example.ewdj_ep3.domain.match.Match;
import com.example.ewdj_ep3.domain.user.User;
import com.example.ewdj_ep3.enums.Outcome;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Predictions")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = { "user", "match" })
public class Prediction {

    @EmbeddedId
    private PredictionId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("matchId")
    @JoinColumn(name = "MATCH_ID")
    private Match match;

    private int scoreHomeTeam;

    private int scoreAwayTeam;

    @Enumerated(EnumType.STRING)
    private Outcome outcome;

    public void setScoreHomeTeam(int scoreHomeTeam) {
        if (match.getMatchDateTime().isBefore(LocalDateTime.now()) || match.getOutcome() != Outcome.SCHEDULED) {
            throw new IllegalStateException("Cannot edit prediction score after match has started");
        }

        this.scoreHomeTeam = scoreHomeTeam;
    }

    public void setScoreAwayTeam(int scoreAwayTeam){
        if (match.getMatchDateTime().isBefore(LocalDateTime.now()) || match.getOutcome() != Outcome.SCHEDULED) {
            throw new IllegalStateException("Cannot edit prediction score after match has started");
        }

        this.scoreAwayTeam = scoreAwayTeam;

    }

    public void setOutcome(Outcome outcome){
        if (match.getMatchDateTime().isBefore(LocalDateTime.now()) || match.getOutcome() != Outcome.SCHEDULED) {
            throw new IllegalStateException("Cannot edit prediction outcome after match has started");
        }

        this.outcome = outcome;
    }
}