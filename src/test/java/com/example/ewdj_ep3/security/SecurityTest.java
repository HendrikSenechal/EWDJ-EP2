package com.example.ewdj_ep3.security;

import com.example.ewdj_ep3.config.SecurityConfig;
import com.example.ewdj_ep3.domain.role.Role;
import com.example.ewdj_ep3.domain.user.User;
import com.example.ewdj_ep3.domain.user.UserService;
import com.example.ewdj_ep3.validator.registration.RegistrationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private UserService userService;

    @BeforeEach
    void setup() {
        Role userRole = Role.builder()
                .name("USER")
                .build();

        User user = User.builder()
                .email("user@test.be")
                .passwordHash(passwordEncoder.encode("12345678"))
                .roles(Set.of(userRole))
                .build();

        when(userService.findByEmail("user@test.be"))
                .thenReturn(user);
    }

    @ParameterizedTest
    @CsvSource({
            "/users/login, loginForm",
            "/users/registration, registrationForm"
    })
    void testGetPublicViews(String url, String expectedViewName) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @WithMockUser
    @Test
    void testAccessWithUserRole() throws Exception {
        mockMvc.perform(get("/matches/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("matches/matchesList"))
                .andExpect(model().attributeExists("matchesList"));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testNoAccessWithWrongRole() throws Exception {
        mockMvc.perform(get("/matches/list"))
                .andExpect(status().isForbidden());
    }

    @WithAnonymousUser
    @Test
    void testNoAccessAnonymous() throws Exception {
        mockMvc.perform(get("/matches/list"))
                .andExpect(redirectedUrlPattern("**/users/login"));
    }

    @Test
    void testWrongPassword() throws Exception {
        mockMvc.perform(formLogin("/users/login")
                        .user("email", "user@test.be")
                        .password("password", "wrongPassword"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/users/login?error"));
    }

    @Test
    void testCorrectPassword() throws Exception {
        mockMvc.perform(formLogin("/users/login")
                        .user("email", "user@test.be")
                        .password("password", "12345678"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/matches/list"));
    }

    @Test
    void testApiIsPublic() throws Exception {
        mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk());
    }
}