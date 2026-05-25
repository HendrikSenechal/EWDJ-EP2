package com.example.ewdj_ep3.seed;

import com.example.ewdj_ep3.domain.match.Match;
import com.example.ewdj_ep3.domain.team.Team;
import com.example.ewdj_ep3.enums.Outcome;
import com.example.ewdj_ep3.persistence.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.ewdj_ep3.persistence.TeamRepository;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DataSeeder implements CommandLineRunner {
    private final TeamRepository teamRepository;

    private final MatchRepository matchRepository;

    private List<Team> teams = new ArrayList<>();

    public DataSeeder(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public void run(String... args) {
        try {
            log.info("\n");
            log.info("✅ Starting DataSeeder \n");

            seedTeams(Paths.get("src/main/resources/seed-data/team-data.csv"));
            log.info("✅ Success: Seeded teams");

            seedMatches(Paths.get("src/main/resources/seed-data/match-data.csv"));
            log.info("✅ Success: Seeded Matches \n");

            log.info("✅ Success: DataSeeder fully succeeded \n");
        } catch (Exception exception) {
            log.error("❌ Probleem bij seeding: ", exception);
        }
    }

    private void seedTeams(Path csvFilePath) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(csvFilePath)) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] teamFields = line.split(",", -1);

                if (teamFields.length != 4)
                    continue;

                teams.add(
                        Team.builder()
                                .name(teamFields[0].trim())
                                .flag(teamFields[1].trim())
                                .worldRanking(Integer.parseInt(teamFields[2].trim()))
                                .worldCupGroup(teamFields[3].trim())
                                .build()
                );
            }
        }

        teamRepository.saveAll(teams);
    }

    private void seedMatches(Path csvFilePath) throws IOException {
        if (matchRepository.count() > 0) return;

        List<Team> teams = teamRepository.findAll();
        List<Match> matches = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(csvFilePath)) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",", -1);

                if (fields.length != 5) continue;

                Team homeTeam = findTeamByName(teams, fields[0].trim());
                Team awayTeam = findTeamByName(teams, fields[1].trim());

                matches.add(
                        Match.builder()
                                .homeTeam(homeTeam)
                                .awayTeam(awayTeam)
                                .worldCupGroup(fields[2].trim())
                                .stadium(fields[3].trim())
                                .matchDateTime(LocalDateTime.parse(fields[4].trim()))
                                .scoreHomeTeam(0)
                                .scoreAwayTeam(0)
                                .outcome(Outcome.SCHEDULED)
                                .build()
                );
            }
        }

        matchRepository.saveAll(matches);
    }

    private Team findTeamByName(List<Team> teams, String name) {
        return teams.stream()
                .filter(team -> team.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Team not found: " + name));
    }
}
