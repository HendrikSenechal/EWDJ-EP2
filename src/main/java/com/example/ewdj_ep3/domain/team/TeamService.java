package com.example.ewdj_ep3.domain.team;

import com.example.ewdj_ep3.persistence.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    public String teamName(){
        Team team = teamRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException());
        return team.getName();
    }
}
