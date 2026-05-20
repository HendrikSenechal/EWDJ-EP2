package com.example.ewdj_ep3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.ewdj_ep3.dto.request.InputDTO;
import com.example.ewdj_ep3.service.HelloService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/hello")
public class HelloController {
    private final HelloService helloService;

    @GetMapping
    public String showHelloForm(InputDTO inputDTO) {
        return "helloForm";
    }

    @PostMapping
    public String processHelloForm(InputDTO inputDTO, Model model) {
        model.addAttribute("helloMessage", helloService.sayHello(inputDTO.name()));
        return "helloResult";
    }
}
