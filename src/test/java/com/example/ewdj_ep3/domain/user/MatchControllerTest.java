package com.example.ewdj_ep3.domain.user;

import com.example.ewdj_ep3.advice.RegistrationValidatorAdvice;
import com.example.ewdj_ep3.domain.match.MatchController;
import com.example.ewdj_ep3.domain.match.MatchService;
import com.example.ewdj_ep3.dto.response.MatchResponseDTO;
import com.example.ewdj_ep3.enums.Outcome;
import com.example.ewdj_ep3.exceptions.MatchNotFoundException;
import com.example.ewdj_ep3.validator.registration.RegistrationValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchController.class)
@Import({
        RegistrationValidator.class,
        RegistrationValidatorAdvice.class
})
public class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatchService matchService;

    @Test
    public void testGetRequest() throws Exception {
        mockMvc.perform(get("/matches/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("matches/matchesList"))
                .andExpect(model().attributeExists("matchesList"));
    }

    @Test
    public void testGetDetailMatch() throws Exception {

        MatchResponseDTO expectedMatch = new MatchResponseDTO(
                1L,
                10L,
                "Mexico",
                "🇲🇽",
                20L,
                "South Africa",
                "🇿🇦",
                LocalDateTime.of(2026, 6, 11, 20, 0),
                "Mexico City Stadium",
                "A",
                0,
                0,
                Outcome.SCHEDULED
        );

        when(matchService.findById(1)).thenReturn(expectedMatch);

        mockMvc.perform(get("/matches/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("matches/matchDetail"))
                .andExpect(model().attributeExists("match"))
                .andExpect(model().attribute("match", expectedMatch));

        verify(matchService).findById(1);
    }

    @Test
    public void testGetNoMatchFound() throws Exception {

        when(matchService.findById(1))
                .thenThrow(new MatchNotFoundException(1));

        mockMvc.perform(get("/matches/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/matches/list"));

        verify(matchService).findById(1);
    }

    @Test
    public void testInvalidIdFormat() throws Exception {

        mockMvc.perform(get("/matches/abc"))
                .andExpect(status().isOk()) // because the controller returns a view
                .andExpect(view().name("error/404"));
    }
}