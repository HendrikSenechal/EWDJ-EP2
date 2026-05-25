package com.example.ewdj_ep3.domain.match;

import com.example.ewdj_ep3.domain.team.Team;
import com.example.ewdj_ep3.enums.Outcome;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@NamedQueries({})
@Table(name = "Matches")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@ToString(exclude = { "id" })
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HOME_TEAM_ID")
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AWAY_TEAM_ID")
    private Team awayTeam;

    private LocalDateTime matchDateTime;

    private String stadium;

    private String worldCupGroup;

    @Setter
    private int scoreHomeTeam;

    @Setter
    private int scoreAwayTeam;

    @Enumerated(EnumType.STRING)
    @Setter
    private Outcome outcome;
}
