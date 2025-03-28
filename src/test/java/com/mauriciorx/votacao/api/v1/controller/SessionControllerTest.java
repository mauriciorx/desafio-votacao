package com.mauriciorx.votacao.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.SessionResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@DisplayName("SessionControllerTest")
@AutoConfigureMockMvc
@SpringBootTest
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private SessionRequestDTO sessionRequestDTO;
    private SessionResponseDTO sessionResponseDTO;

    private VoteRequestDTO voteRequestDTO;

    private final Integer time = 120;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        LocalDateTime creationDate = LocalDateTime.of(2025, 03, 27, 23, 03, 00);

        sessionRequestDTO = SessionRequestDTO.builder()
                                            .time(time)
                                            .agendaId(1L)
                                            .creationDate(creationDate)
                                            .build();

        sessionResponseDTO = SessionResponseDTO.builder()
                                            .id(1L)
                                            .time(time)
                                            .agendaId(1L)
                                            .creationDate(creationDate)
                                            .build();

        voteRequestDTO = VoteRequestDTO.builder()
                                        .sessionId(1L)
                                        .associateId(1L)
                                        .voteApproved(true)
                                        .build();
    }

    @Test
    @DisplayName("shouldCreateSessionSuccessfully")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-agenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldCreateSessionSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/session/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("shouldNotCreateSessionSuccessfully")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldNotCreateSessionSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/session/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("shouldVoteSuccessfully")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-agenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/setup-session.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/setup-associate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldVoteSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/session/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voteRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("shouldFindSessionById")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-agenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/setup-session.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldFindSessionById() throws Exception {
        mockMvc.perform(get("/api/v1/session/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(sessionResponseDTO)));
    }

    @Test
    @DisplayName("shouldFindAllSessions")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-agenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/setup-session.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldFindAllSessions() throws Exception {
        mockMvc.perform(get("/api/v1/session/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(sessionResponseDTO))));
    }
}
