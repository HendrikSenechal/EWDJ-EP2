package com.example.ewdj_ep3.domain.user;

import com.example.ewdj_ep3.domain.role.RoleService;
import com.example.ewdj_ep3.dto.request.InputLoginDTO;
import com.example.ewdj_ep3.dto.request.InputRegistrationDTO;
import com.example.ewdj_ep3.exceptions.InvalidCredentialsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("inputLoginDTO", new InputLoginDTO());
        return "loginForm";
    }

    @PostMapping("/login")
    //Definieer de naam van in ModelAttribute indien het niet gelijk is aan de naam van zijn klasse
    public String loginUser(@ModelAttribute("inputLoginDTO") @Valid InputLoginDTO inputLoginDTO, BindingResult result) {
        if (result.hasErrors())
            return "loginForm";
        //userService login method
        throw new InvalidCredentialsException("test@mail.be", "AUTH_401", "Couldnt find users with this email:");
    }

    @GetMapping("/registration")
    public String showRegisterForm(Model model) {
        model.addAttribute("inputRegistrationDTO", new InputRegistrationDTO());
        model.addAttribute("allRoles", roleService.findAll());
        return "registrationForm";
    }


    @PostMapping("/registration")
    //Definieer de naam van in ModelAttribute indien het niet gelijk is aan de naam van zijn klasse
    public String saveUser(@ModelAttribute("inputRegistrationDTO") @Valid InputRegistrationDTO inputRegistrationDTO, BindingResult result) {
        if (result.hasErrors())
            return "registrationForm";
        userService.saveUser(inputRegistrationDTO);
        return "redirect:/users/login";
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ModelAndView handleInvalidCredentialsException(
            InvalidCredentialsException ex) {

        ModelAndView model = new ModelAndView("loginForm");

        model.addObject("inputLoginDTO", new InputLoginDTO());
        model.addObject("errCode", ex.getErrCode());
        model.addObject("errMsg", ex.getErrMsg());
        model.addObject("email", ex.getEmail());

        return model;
    }
}
