package com.example.ewdj_ep3.domain.team;

import com.example.ewdj_ep3.domain.match.Match;
import com.example.ewdj_ep3.utils.FlagUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({})
@Table(name = "Teams")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "homeMatches", "awayMatches"})
@ToString(exclude = {"id", "homeMatches", "awayMatches"})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String flag;

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

    public String getFlagEmoji() {
        return FlagUtil.toEmoji(flag);
    }
}
