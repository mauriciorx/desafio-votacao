package com.mauriciorx.votacao.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AgendaResponseDTO;
import com.mauriciorx.votacao.api.v1.service.IAgendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AgendaController.class)
public class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IAgendaService agendaService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAgenda() throws Exception {
        AgendaRequestDTO requestDTO = new AgendaRequestDTO("Test Agenda", "Description");
        AgendaResponseDTO responseDTO = new AgendaResponseDTO(1L, "Test Agenda", "Description");

        when(agendaService.create(any(AgendaRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/agenda/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Agenda"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description"));
    }

    @Test
    void testFindById() throws Exception {
        Long agendaId = 1L;
        AgendaResponseDTO responseDTO = new AgendaResponseDTO(agendaId, "Test Agenda", "Description");

        when(agendaService.findById(agendaId)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/agenda/{agendaId}", agendaId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Agenda"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description"));
    }

    @Test
    void testFindAll() throws Exception {
        when(agendaService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/agenda/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void testGenerateVoteOutcome() throws Exception {
        Long agendaId = 1L;

        when(agendaService.generateVoteOutcome(agendaId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/agenda/outcome/{agendaId}", agendaId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}