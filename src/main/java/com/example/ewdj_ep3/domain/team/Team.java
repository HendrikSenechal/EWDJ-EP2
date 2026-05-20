package com.example.ewdj_ep3.domain.team;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NamedQueries({})
@Table(name = "Teams")
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "id", "registrations", "vendors" })
@ToString(exclude = { "id", "registrations" })
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
