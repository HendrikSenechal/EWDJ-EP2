package com.example.ewdj_ep3.domain.match;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;

    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("matchesList", matchService.findAll());
        return "matches/matchesList";
    }

    @GetMapping("/{id}")
    public String showMatch(@PathVariable("id") Long matchId, Model model) {
        model.addAttribute("match", matchService.findById(matchId));
        return "matches/matchDetail";
    }

}
