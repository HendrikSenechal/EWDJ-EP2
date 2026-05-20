package com.example.ewdj_ep3.domain.index;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {
    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/hello";
    }
}
