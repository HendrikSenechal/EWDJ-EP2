package com.example.ewdj_ep3.domain.competition;

import com.example.ewdj_ep3.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Competitions")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "id" })
@ToString(exclude = { "id" })
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OWNER_ID")
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "COMPETITION_USERS",
            joinColumns = @JoinColumn(name = "COMPETITION_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @Builder.Default
    private Set<User> users = new HashSet<>();
}