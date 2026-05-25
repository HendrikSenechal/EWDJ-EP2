package com.example.ewdj_ep3.domain.team;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

@Entity
@NamedQueries({})
@Table(name = "Teams")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "id", "registrations", "vendors" })
@ToString(exclude = { "id", "registrations" })
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int worldRanking;

    private String worldCupGroup;
}
