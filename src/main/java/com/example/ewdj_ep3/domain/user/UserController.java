package com.example.ewdj_ep3.domain.user;

import com.example.ewdj_ep3.domain.role.RoleService;
import com.example.ewdj_ep3.dto.request.UserInputDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userInputDTO", new UserInputDTO());
        model.addAttribute("allRoles", roleService.findAll());
        return "registrationForm";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "loginForm";
    }

    @PostMapping("/save")
    //Definieer de naam van in ModelAttribute indien het niet gelijk is aan de naam van zijn klasse
    public String saveUser(@ModelAttribute("userInputDTO") @Valid UserInputDTO userInputDTO, BindingResult result) {
        if (result.hasErrors())
            return "registrationForm";
        userService.saveUser(userInputDTO);
        return "redirect:/hello";
    }
}
