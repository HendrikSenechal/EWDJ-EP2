package com.example.ewdj_ep3.domain.user;

import com.example.ewdj_ep3.domain.competition.Competition;
import com.example.ewdj_ep3.domain.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({})
@Table(name = "Users")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@ToString(exclude = { "id", "roles" })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastname;

    private String email;

    private String passwordHash;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @Builder.Default
    private Set<Competition> ownedCompetitions = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @Builder.Default
    private Set<Competition> competitions = new HashSet<>();
}
