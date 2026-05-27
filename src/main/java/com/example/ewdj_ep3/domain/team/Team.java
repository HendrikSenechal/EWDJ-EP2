package com.example.ewdj_ep3.domain.team;

import com.example.ewdj_ep3.domain.match.Match;
import com.example.ewdj_ep3.utils.FlagUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Stream;

@Entity
@NamedQueries({})
@Table(name = "Teams")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@ToString(exclude = { "id", "homeMatches", "homeMatches" })
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
