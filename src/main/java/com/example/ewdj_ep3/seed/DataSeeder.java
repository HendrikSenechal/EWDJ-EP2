package com.example.ewdj_ep3.seed;

import com.example.ewdj_ep3.domain.team.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ewdj_ep3.persistence.TeamRepository;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DataSeeder implements CommandLineRunner {
    private final TeamRepository teamRepository;

    private List<Team> teams = new ArrayList<>();

    public DataSeeder(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void run(String... args) {
        try {
            seedTeams(Paths.get("src/main/resources/seed-data/team-data.csv"));
            log.info("Teams geseed");
        } catch (Exception exception) {
            log.error("Probleem bij seeding", exception);
        }
    }

    private void seedTeams(Path csvFilePath) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(csvFilePath)) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] teamFields = line.split(",", -1);

                if (teamFields.length != 3)
                    continue;

                teams.add(
                        Team.builder()
                                .name(teamFields[0].trim())
                                .worldRanking(Integer.parseInt(teamFields[1].trim()))
                                .worldCupGroup(teamFields[2].trim())
                                .build()
                );
            }
        }

        teamRepository.saveAll(teams);
    }
}
