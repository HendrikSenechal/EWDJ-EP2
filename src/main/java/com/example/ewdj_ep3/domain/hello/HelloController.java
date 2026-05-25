package com.example.ewdj_ep3.domain.hello;

import com.example.ewdj_ep3.domain.team.TeamService;
import com.example.ewdj_ep3.dto.request.InputDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/hello")
public class HelloController {
    private final HelloService helloService;
    private final TeamService teamService;

    @GetMapping
    public String showHelloForm(InputDTO inputDTO) {
        log.info("get hello");
        return "helloForm";
    }

    @PostMapping
    public String processHelloForm(InputDTO inputDTO, Model model) {
        model.addAttribute("helloMessage", helloService.sayHello(inputDTO.name()));
        model.addAttribute("teamName", teamService.teamName());

        log.info(teamService.teamName());


        log.info("post hello, Name submitted: {}", inputDTO!=null? inputDTO.name(): "null");

        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");

        log.debug("inside hello post method");
        return "helloResult";
    }
}
