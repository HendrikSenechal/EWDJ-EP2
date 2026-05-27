package com.example.ewdj_ep3.domain.match;

import com.example.ewdj_ep3.advice.RegistrationValidatorAdvice;
import com.example.ewdj_ep3.dto.request.InputMatchDTO;
import com.example.ewdj_ep3.dto.request.UpdateMatchDTO;
import com.example.ewdj_ep3.dto.response.MatchCapacityDTO;
import com.example.ewdj_ep3.dto.response.MatchResponseDTO;
import com.example.ewdj_ep3.enums.Outcome;
import com.example.ewdj_ep3.exceptions.MatchNotFoundException;
import com.example.ewdj_ep3.validator.registration.RegistrationValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({
        RegistrationValidator.class,
        RegistrationValidatorAdvice.class
})
class MatchRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MatchService matchService;

    @MockitoBean
    private RegistrationValidator registrationValidator;

    @Autowired
    private ObjectMapper objectMapper;

    private final Long ID = 1L;

    private MatchResponseDTO matchDto() {
        return new MatchResponseDTO(
                ID,
                1L,
                "France",
                "🇫🇷",
                2L,
                "Spain",
                "🇪🇸",
                LocalDateTime.of(2026, 6, 14, 20, 0),
                "Lusail Stadium",
                88000,
                "A",
                0,
                0,
                Outcome.SCHEDULED
        );
    }

    @Test
    void testGetAllMatches_emptyList() throws Exception {
        Mockito.when(matchService.findAll())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        Mockito.verify(matchService).findAll();
    }

    @Test
    void testGetAllMatches_notEmpty() throws Exception {
        Mockito.when(matchService.findAll())
                .thenReturn(List.of(matchDto()));

        mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(ID))
                .andExpect(jsonPath("$[0].homeTeamName").value("France"))
                .andExpect(jsonPath("$[0].awayTeamName").value("Spain"))
                .andExpect(jsonPath("$[0].stadium").value("Lusail Stadium"))
                .andExpect(jsonPath("$[0].stadiumCapacity").value(88000));

        Mockito.verify(matchService).findAll();
    }

    @Test
    void testGetMatch_isOk() throws Exception {
        Mockito.when(matchService.findById(ID))
                .thenReturn(matchDto());

        mockMvc.perform(get("/api/matches/" + ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.homeTeamName").value("France"))
                .andExpect(jsonPath("$.awayTeamName").value("Spain"));

        Mockito.verify(matchService).findById(ID);
    }

    @Test
    void testGetMatch_notFound() throws Exception {
        Mockito.when(matchService.findById(ID))
                .thenThrow(new MatchNotFoundException(ID));

        mockMvc.perform(get("/api/matches/" + ID))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/matches/list"));

        Mockito.verify(matchService).findById(ID);
    }

    @Test
    void testGetMatchesByDate() throws Exception {
        LocalDate date = LocalDate.of(2026, 6, 14);

        Mockito.when(matchService.findByDate(date))
                .thenReturn(List.of(matchDto()));

        mockMvc.perform(get("/api/matches/date/2026-06-14"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID))
                .andExpect(jsonPath("$[0].stadium").value("Lusail Stadium"));

        Mockito.verify(matchService).findByDate(date);
    }

    @Test
    void testGetStadiumCapacityById() throws Exception {
        Mockito.when(matchService.findStadiumCapacityById(ID))
                .thenReturn(new MatchCapacityDTO(ID, 88000));

        mockMvc.perform(get("/api/matches/" + ID + "/capacity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.stadiumCapacity").value(88000));

        Mockito.verify(matchService).findStadiumCapacityById(ID);
    }

    @Test
    void testCreateMatch() throws Exception {
        InputMatchDTO input = new InputMatchDTO(
                1L,
                2L,
                LocalDateTime.of(2026, 6, 14, 20, 0),
                "Lusail Stadium",
                88000,
                "A"
        );

        Mockito.when(matchService.save(any(InputMatchDTO.class)))
                .thenReturn(matchDto());

        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.homeTeamName").value("France"))
                .andExpect(jsonPath("$.awayTeamName").value("Spain"))
                .andExpect(jsonPath("$.stadiumCapacity").value(88000));

        Mockito.verify(matchService).save(any(InputMatchDTO.class));
    }

    @Test
    void testUpdateMatch() throws Exception {
        UpdateMatchDTO update = new UpdateMatchDTO(
                1L,
                2L,
                LocalDateTime.of(2026, 6, 14, 21, 30),
                "Lusail Iconic Stadium",
                90000,
                "B",
                2,
                1,
                Outcome.HOME_VICTORY
        );

        MatchResponseDTO updated = new MatchResponseDTO(
                ID,
                1L,
                "France",
                "🇫🇷",
                2L,
                "Spain",
                "🇪🇸",
                LocalDateTime.of(2026, 6, 14, 21, 30),
                "Lusail Iconic Stadium",
                90000,
                "B",
                2,
                1,
                Outcome.HOME_VICTORY
        );

        Mockito.when(matchService.update(eq(ID), any(UpdateMatchDTO.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/matches/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.stadium").value("Lusail Iconic Stadium"))
                .andExpect(jsonPath("$.scoreHomeTeam").value(2))
                .andExpect(jsonPath("$.scoreAwayTeam").value(1));

        Mockito.verify(matchService).update(eq(ID), any(UpdateMatchDTO.class));
    }

    @Test
    void testDeleteMatch() throws Exception {
        Mockito.when(matchService.deleteById(ID))
                .thenReturn(matchDto());

        mockMvc.perform(delete("/api/matches/" + ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.homeTeamName").value("France"));

        Mockito.verify(matchService).deleteById(ID);
    }
}