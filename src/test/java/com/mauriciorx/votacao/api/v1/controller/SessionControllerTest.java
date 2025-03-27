package com.mauriciorx.votacao.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.SessionResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteResponseDTO;
import com.mauriciorx.votacao.api.v1.enums.VoteEnum;
import com.mauriciorx.votacao.api.v1.service.ISessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ISessionService sessionService;

    private SessionResponseDTO sessionResponseDTO;
    private VoteResponseDTO voteResponseDTO;

    private final Integer time = 60;

    @BeforeEach
    void setUp() {
        sessionResponseDTO = new SessionResponseDTO(1L, time, 1L, LocalDateTime.now());
        voteResponseDTO = new VoteResponseDTO(1L, 1L, 1L, VoteEnum.YES.getValue());
    }

    @Test
    void testCreate() throws Exception {
        when(sessionService.create(any(SessionRequestDTO.class))).thenReturn(sessionResponseDTO);

        SessionRequestDTO requestDTO = new SessionRequestDTO(time, 1L, LocalDateTime.now());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/session/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(sessionResponseDTO.getId()));
    }

    @Test
    void testVote() throws Exception {
        when(sessionService.vote(any(VoteRequestDTO.class))).thenReturn(voteResponseDTO);

        VoteRequestDTO voteRequestDTO = new VoteRequestDTO(1L, 1L, true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/session/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voteRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.voteResult").value(VoteEnum.YES.getValue()));
    }

    @Test
    void testFindById() throws Exception {
        when(sessionService.findById(1L)).thenReturn(sessionResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/session/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(sessionResponseDTO.getId()));
    }

    @Test
    void testFindAll() throws Exception {
        when(sessionService.findAll()).thenReturn(Collections.singletonList(sessionResponseDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/session/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(sessionResponseDTO.getId()));
    }
}