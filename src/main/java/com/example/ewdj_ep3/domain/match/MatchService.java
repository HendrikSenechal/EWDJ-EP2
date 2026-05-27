package com.example.ewdj_ep3.domain.match;

import com.example.ewdj_ep3.dto.response.MatchResponseDTO;
import com.example.ewdj_ep3.exceptions.MatchNotFoundException;
import com.example.ewdj_ep3.mapper.MatchMapper;
import com.example.ewdj_ep3.persistence.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    public List<MatchResponseDTO> findAll() {

        log.info("Fetching all matches");

        return matchRepository.findAll()
                .stream()
                .map(MatchMapper::toDTO)
                .toList();
    }

    public MatchResponseDTO findById(Long matchId) throws MatchNotFoundException {

        log.info("Fetching match with id {}", matchId);

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(matchId));

        return MatchMapper.toDTO(match);
    }

    public MatchResponseDTO save(Match match) {
        Match savedMatch = matchRepository.save(match);

        log.info("Saved match with id {}", savedMatch.getId());

        return MatchMapper.toDTO(savedMatch);
    }

    public MatchResponseDTO deleteById(Long matchId) throws MatchNotFoundException {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(matchId));

        matchRepository.delete(match);

        log.info("Deleted match with id {}", matchId);

        return MatchMapper.toDTO(match);
    }
}