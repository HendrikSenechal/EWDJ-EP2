package com.example.ewdj_ep3.domain.team;

import com.example.ewdj_ep3.domain.hello.HelloService;
import com.example.ewdj_ep3.dto.request.InputDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public String showTeamName(Model model) {

        log.info("get team name");

        String teamName = teamService.teamName();

        model.addAttribute("teamName", teamName);

        return "helloResult";
    }

}
