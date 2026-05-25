package com.example.ewdj_ep3.domain.team;

import com.example.ewdj_ep3.domain.match.Match;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({})
@Table(name = "Teams")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@ToString(exclude = { "id" })
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int worldRanking;

    private String worldCupGroup;

    @OneToMany(mappedBy = "homeTeam")
    @JsonIgnore
    @Builder.Default
    private Set<Match> homeMatches = new HashSet<>();

    @OneToMany(mappedBy = "awayTeam")
    @JsonIgnore
    @Builder.Default
    private Set<Match> awayMatches = new HashSet<>();
}
