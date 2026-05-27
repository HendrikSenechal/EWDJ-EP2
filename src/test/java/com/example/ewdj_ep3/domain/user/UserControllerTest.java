package com.example.ewdj_ep3.domain.user;

import static com.example.ewdj_ep3.init.InitRegistration.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import com.example.ewdj_ep3.advice.RegistrationValidatorAdvice;
import com.example.ewdj_ep3.domain.role.RoleService;
import com.example.ewdj_ep3.dto.request.InputRegistrationDTO;
import com.example.ewdj_ep3.init.InputRegistrationDTOBuilder;
import com.example.ewdj_ep3.validator.registration.RegistrationValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@Import({
        RegistrationValidator.class,
        RegistrationValidatorAdvice.class
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private RoleService roleService;

    @Test
    void testGetRequest() throws Exception {
        mockMvc.perform(get("/users/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registrationForm"))
                .andExpect(model().attributeExists("inputRegistrationDTO"))
                .andExpect(model().attributeExists("allRoles"));
    }

    @Test
    void testPostRequestValidRegistration() throws Exception {
        InputRegistrationDTO validRegistration =
                InputRegistrationDTOBuilder.builder().build().toDTO();

        mockMvc.perform(post("/users/registration")
                        .flashAttr("inputRegistrationDTO", validRegistration))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/login"));

        verify(userService).saveUser(any(InputRegistrationDTO.class));
    }

    private static Stream<Arguments> invalidRegistrationData() {
        return Stream.of(
                Arguments.of("", OK_LASTNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, new String[]{"name"}),
                Arguments.of(OK_NAME, "", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, new String[]{"lastname"}),
                Arguments.of(OK_NAME, OK_LASTNAME, OK_PASSWORD, "12345678", OK_EMAIL, new String[]{"password"}),
                Arguments.of(OK_NAME, OK_LASTNAME, OK_PASSWORD, "", OK_EMAIL, new String[]{"confirmPassword"}),
                Arguments.of(OK_NAME, OK_LASTNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, "test", new String[]{"email"}),
                Arguments.of(OK_NAME, OK_LASTNAME, "1234", "123566", "test", new String[]{"password", "email"}),
                Arguments.of("12345", "12345", "", " ", "test",
                        new String[]{"name", "lastname", "password", "confirmPassword", "email"})
        );
    }

    @ParameterizedTest
    @MethodSource("invalidRegistrationData")
    void testPostRequestInvalidRegistration(
            String name,
            String lastname,
            String password,
            String confirmPassword,
            String email,
            String[] expectedErrors
    ) throws Exception {

        InputRegistrationDTO invalidRegistration =
                InputRegistrationDTOBuilder.builder()
                        .name(name)
                        .lastname(lastname)
                        .email(email)
                        .password(password)
                        .confirmPassword(confirmPassword)
                        .build()
                        .toDTO();

        mockMvc.perform(post("/users/registration")
                        .flashAttr("inputRegistrationDTO", invalidRegistration))
                .andExpect(status().isOk())
                .andExpect(view().name("registrationForm"))
                .andExpect(model().attributeHasFieldErrors("inputRegistrationDTO", expectedErrors));

        verify(userService, never()).saveUser(any(InputRegistrationDTO.class));
    }
}