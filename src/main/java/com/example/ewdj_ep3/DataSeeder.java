package com.example.ewdj_ep3;

import com.example.ewdj_ep3.domain.team.Team;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ewdj_ep3.persistence.TeamRepository;

@Component
public class DataSeeder implements CommandLineRunner {
    private final TeamRepository teamRepository;

    public DataSeeder(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) {
        Team team = Team.builder().name("Belgium").build();
        teamRepository.save(team);
    }
}
