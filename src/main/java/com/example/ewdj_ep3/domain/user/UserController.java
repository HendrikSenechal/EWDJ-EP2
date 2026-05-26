package com.example.ewdj_ep3.domain.user;

import com.example.ewdj_ep3.domain.hello.HelloService;
import com.example.ewdj_ep3.domain.role.RoleService;
import com.example.ewdj_ep3.dto.request.InputDTO;
import com.example.ewdj_ep3.dto.request.UserInputDTO;
import com.example.ewdj_ep3.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("userDTO", new UserInputDTO());
        model.addAttribute("allRoles", roleService.findAll());
        return "registrationForm";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "loginForm";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute UserInputDTO userInputDTO) {
        userService.saveUser(userInputDTO);
        return "redirect:/hello";
    }
}
