package com.example.ewdj_ep3.domain.match;

import com.example.ewdj_ep3.dto.request.InputMatchDTO;
import com.example.ewdj_ep3.dto.request.UpdateMatchDTO;
import com.example.ewdj_ep3.dto.response.MatchResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches")
public class MatchRestController {

    private final MatchService matchService;

    @GetMapping
    public List<MatchResponseDTO> getAllMatches() {
        return matchService.findAll();
    }

    @GetMapping("/{id}")
    public MatchResponseDTO getMatch(@PathVariable Long id) {
        return matchService.findById(id);
    }

    @PostMapping
    public MatchResponseDTO createMatch(@Valid @RequestBody InputMatchDTO inputMatchDTO) {
        return matchService.save(inputMatchDTO);
    }

    @PutMapping("/{id}")
    public MatchResponseDTO updateMatch(@PathVariable Long id, @Valid @RequestBody UpdateMatchDTO updateMatchDTO) { return matchService.update(id, updateMatchDTO); }

    @DeleteMapping("/{id}")
    public MatchResponseDTO deleteMatch(@PathVariable Long id) {
        return matchService.deleteById(id);
    }
}