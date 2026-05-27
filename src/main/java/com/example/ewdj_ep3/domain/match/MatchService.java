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

    public List<MatchResponseDTO> findAllMatches(){
        return matchRepository.findAll()
                .stream()
                .map(s -> MatchMapper.toDTO(s))
                .toList();
    }

    public MatchResponseDTO findById(Integer matchId) throws MatchNotFoundException {
        Match match = matchRepository.findById(Long.valueOf(matchId))
                .orElseThrow(() -> new MatchNotFoundException(matchId));
        return MatchMapper.toDTO(match);
    }
}
