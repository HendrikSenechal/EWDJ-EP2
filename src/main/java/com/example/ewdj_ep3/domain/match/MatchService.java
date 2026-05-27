package com.example.ewdj_ep3.domain.match;

import com.example.ewdj_ep3.domain.team.Team;
import com.example.ewdj_ep3.dto.request.InputMatchDTO;
import com.example.ewdj_ep3.dto.request.UpdateMatchDTO;
import com.example.ewdj_ep3.dto.response.MatchCapacityDTO;
import com.example.ewdj_ep3.dto.response.MatchResponseDTO;
import com.example.ewdj_ep3.exceptions.MatchNotFoundException;
import com.example.ewdj_ep3.exceptions.TeamNotFoundException;
import com.example.ewdj_ep3.mapper.MatchMapper;
import com.example.ewdj_ep3.persistence.MatchRepository;
import com.example.ewdj_ep3.persistence.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public void throwsException() {
        throw new MatchNotFoundException(1L);
    }

    public List<MatchResponseDTO> findAll() {

        log.info("Fetching all matches");

        return matchRepository.findAll()
                .stream()
                .map(MatchMapper::toDTO)
                .toList();
    }

    public List<MatchResponseDTO> findByDate(LocalDate date) {

        log.info("Fetching all matches");

        return matchRepository.findAll()
                .stream()
                .filter(match -> match.getMatchDateTime().toLocalDate().equals(date))
                .map(MatchMapper::toDTO)
                .toList();
    }

    public MatchCapacityDTO findStadiumCapacityById(Long id) throws MatchNotFoundException {

        log.info("Fetching match with id {}", id);

        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));

        return new MatchCapacityDTO(
                match.getId(),
                match.getStadiumCapacity()
        );
    }

    public MatchResponseDTO findById(Long id) throws MatchNotFoundException {

        log.info("Fetching match with id {}", id);

        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));

        return MatchMapper.toDTO(match);
    }

    public MatchResponseDTO save(InputMatchDTO inputMatchDTO) {
        Team homeTeam = teamRepository.findById(inputMatchDTO.homeTeamId())
                .orElseThrow(() -> new TeamNotFoundException(inputMatchDTO.homeTeamId()));
        Team awayTeam = teamRepository.findById(inputMatchDTO.awayTeamId())
                .orElseThrow(() -> new TeamNotFoundException(inputMatchDTO.awayTeamId()));

        Match newMatch = MatchMapper.toEntity(inputMatchDTO, homeTeam, awayTeam);

        Match savedMatch = matchRepository.save(newMatch);

        log.info("Saved match with id {}", savedMatch.getId());

        return MatchMapper.toDTO(savedMatch);
    }

    public MatchResponseDTO update(Long id, UpdateMatchDTO updateMatchDTO) {
        Match existingMatch = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));
        Team homeTeam = teamRepository.findById(updateMatchDTO.homeTeamId())
                .orElseThrow(() -> new TeamNotFoundException(updateMatchDTO.homeTeamId()));
        Team awayTeam = teamRepository.findById(updateMatchDTO.awayTeamId())
                .orElseThrow(() -> new TeamNotFoundException(updateMatchDTO.awayTeamId()));

        Match newMatch = MatchMapper.updateEntity(existingMatch, updateMatchDTO, homeTeam, awayTeam);

        Match savedMatch = matchRepository.save(newMatch);

        log.info("Saved match with id {}", savedMatch.getId());

        return MatchMapper.toDTO(savedMatch);
    }

    public MatchResponseDTO deleteById(Long id) throws MatchNotFoundException {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));

        matchRepository.delete(match);

        log.info("Deleted match with id {}", id);

        return MatchMapper.toDTO(match);
    }
}