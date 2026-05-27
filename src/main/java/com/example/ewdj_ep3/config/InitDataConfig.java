package com.example.ewdj_ep3.config;

import com.example.ewdj_ep3.domain.competition.Competition;
import com.example.ewdj_ep3.domain.match.Match;
import com.example.ewdj_ep3.domain.prediction.Prediction;
import com.example.ewdj_ep3.domain.prediction.PredictionId;
import com.example.ewdj_ep3.domain.role.Role;
import com.example.ewdj_ep3.domain.team.Team;
import com.example.ewdj_ep3.domain.user.User;
import com.example.ewdj_ep3.enums.Outcome;
import com.example.ewdj_ep3.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class InitDataConfig implements CommandLineRunner {
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PredictionRepository predictionRepository;
    private final CompetitionRepository competitionRepository;
    private List<Team> teams = new ArrayList<>();

    @Override
    public void run(String... args) {
        try {
            log.info("\n");
            log.info("✅ Starting DataSeeder \n");

            seedRoles();
            log.info("✅ Success: Seeded Roles");

            seedUsers(Paths.get("src/main/resources/seed-data/user-data.csv"));
            log.info("✅ Success: Seeded Users");

            seedCompetitions();
            log.info("✅ Success: Seeded Competitions");

            seedTeams(Paths.get("src/main/resources/seed-data/team-data.csv"));
            log.info("✅ Success: Seeded teams");

            seedMatches(Paths.get("src/main/resources/seed-data/match-data.csv"));
            log.info("✅ Success: Seeded Matches");

            seedPredictions();
            log.info("✅ Success: Seeded Matches\n");

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

                if (fields.length != 6) continue;

                Team homeTeam = findTeamByName(teams, fields[0].trim());
                Team awayTeam = findTeamByName(teams, fields[1].trim());

                matches.add(
                        Match.builder()
                                .homeTeam(homeTeam)
                                .awayTeam(awayTeam)
                                .worldCupGroup(fields[2].trim())
                                .stadium(fields[3].trim())
                                .stadiumCapacity(Integer.parseInt(fields[4].trim()))
                                .matchDateTime(LocalDateTime.parse(fields[5].trim()))
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

    private void seedRoles() {
        if (roleRepository.count() > 0) return;

        List<Role> roles = List.of(
                Role.builder().name("ADMIN").build(),
                Role.builder().name("USER").build()
        );

        roleRepository.saveAll(roles);
    }

    private void seedUsers(Path csvFilePath) throws IOException {

        if (userRepository.count() > 0) return;

        List<User> users = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(csvFilePath)) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] fields = line.split(",", -1);

                if (fields.length != 5)
                    continue;

                Set<Role> roles = Arrays.stream(fields[4].trim().split("\\|"))
                        .map(String::trim)
                        .map(roleName -> roleRepository.findByName(roleName)
                                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName)))
                        .collect(Collectors.toSet());

                users.add(
                        User.builder()
                                .name(fields[0].trim())
                                .lastname(fields[1].trim())
                                .email(fields[2].trim())
                                .passwordHash(passwordEncoder.encode(fields[3].trim()))
                                .roles(roles)
                                .build()
                );
            }
        }

        userRepository.saveAll(users);
    }

    private void seedPredictions() {

        if (predictionRepository.count() > 0) return;

        List<User> users = userRepository.findAll();
        List<Match> matches = matchRepository.findAll();

        if (matches.isEmpty()) return;

        Match firstMatch = matches.stream()
                .sorted(Comparator.comparing(Match::getMatchDateTime))
                .findFirst()
                .orElseThrow();

        List<Prediction> predictions = new ArrayList<>();

        for (int i = 0; i < users.size() / 2; i++) {

            User user = users.get(i);

            int homeScore = (int) (Math.random() * 5);
            int awayScore = (int) (Math.random() * 5);

            Outcome outcome;

            if (homeScore > awayScore) {
                outcome = Outcome.HOME_VICTORY;
            } else if (awayScore > homeScore) {
                outcome = Outcome.AWAY_VICTORY;
            } else {
                outcome = Outcome.DRAW;
            }

            predictions.add(
                    Prediction.builder()
                            .id(new PredictionId(
                                    user.getId(),
                                    firstMatch.getId()
                            ))
                            .user(user)
                            .match(firstMatch)
                            .scoreHomeTeam(homeScore)
                            .scoreAwayTeam(awayScore)
                            .outcome(outcome)
                            .build()
            );
        }

        predictionRepository.saveAll(predictions);
    }

    private void seedCompetitions() {

        if (competitionRepository.count() > 0) return;

        User owner = userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals("hendrik@gmail.com"))
                .findFirst()
                .orElseThrow();

        Set<User> competitionUsers = userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getName().equals("USER")))
                .filter(user -> user.getRoles().stream()
                        .noneMatch(role -> role.getName().equals("ADMIN")))
                .limit(4)
                .collect(Collectors.toSet());

        Competition competition = Competition.builder()
                .owner(owner)
                .name("World Cup Legends")
                .code("WC2026")
                .users(competitionUsers)
                .build();

        competitionRepository.save(competition);
    }
}
