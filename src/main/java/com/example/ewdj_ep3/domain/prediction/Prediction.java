package com.example.ewdj_ep3.domain.prediction;

import com.example.ewdj_ep3.domain.match.Match;
import com.example.ewdj_ep3.domain.user.User;
import com.example.ewdj_ep3.enums.Outcome;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Predictions")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
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

    @Setter
    private int scoreHomeTeam;

    @Setter
    private int scoreAwayTeam;

    @Enumerated(EnumType.STRING)
    @Setter
    private Outcome outcome;
}