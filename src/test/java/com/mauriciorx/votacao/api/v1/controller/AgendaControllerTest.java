package com.mauriciorx.votacao.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AgendaResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteOutcomeResponseDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@ActiveProfiles("test")
@DisplayName("AgendaControllerTest")
@AutoConfigureMockMvc
@SpringBootTest
public class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    private AgendaRequestDTO agendaRequestDTO;
    private AgendaResponseDTO agendaResponseDTO;
    private VoteOutcomeResponseDTO voteOutcomeResponseDTO;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String title = "Agenda 1";
        String description = "Description for Agenda 1";

        agendaRequestDTO = AgendaRequestDTO.builder().name(title).description(description).build();

        agendaResponseDTO = AgendaResponseDTO.builder().id(1L).name(title).description(description).build();

        voteOutcomeResponseDTO = VoteOutcomeResponseDTO.builder()
                                                        .title(title)
                                                        .description(description)
                                                        .approvedVotes(1L)
                                                        .rejectedVotes(0L)
                                                        .overall(1L)
                                                        .outcome("APROVADO")
                                                        .build();
    }

    @Test
    @DisplayName("shouldCreateAgendaSuccessfully")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-agenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldCreateAgendaSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/agenda/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agendaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("shouldNotCreateAgendaSuccessfully")
    @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldNotCreateAgendaSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/agenda/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(AgendaRequestDTO.builder().build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("shouldFindById")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-agenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldFindById() throws Exception {
        mockMvc.perform(get("/api/v1/agenda/{agendaId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(agendaResponseDTO)));
    }

    @Test
    @DisplayName("shouldFindAll")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-agenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/agenda/", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(agendaResponseDTO))));
    }

    @Test
    @DisplayName("shouldGenerateVoteOutcomes")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-agenda.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/setup-associate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/setup-session.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/setup-vote.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldGenerateVoteOutcome() throws Exception {
        mockMvc.perform(get("/api/v1/agenda/outcome/{agendaId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(voteOutcomeResponseDTO)));
    }
}
