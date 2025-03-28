package com.mauriciorx.votacao.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mauriciorx.votacao.api.v1.dto.request.AssociateRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AssociateResponseDTO;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AssociateControllerTest")
public class AssociateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AssociateRequestDTO associateRequestDTO;
    private AssociateResponseDTO associateResponseDTO;

    private final String cpf = "241.690.100-19";

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String name = "Associate 1";

        associateRequestDTO = AssociateRequestDTO.builder().name(name).cpf(cpf).build();
        associateResponseDTO = AssociateResponseDTO.builder().id(1L).name(name).cpf(cpf).build();
    }

    @Test
    @DisplayName("shouldCreateAssociateSuccessfully")
    @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldCreateAssociateSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/associate/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(associateRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("shouldFindAssociateByIdSuccessfully")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-associate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldFindAssociateByIdSuccessfully() throws Exception {
        mockMvc.perform(get("/api/v1/associate/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(associateResponseDTO)));
    }

    @Test
    @DisplayName("shouldFindAssociateByCpfSuccessfully")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-associate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldFindAssociateByCpfSuccessfully() throws Exception {
        mockMvc.perform(get("/api/v1/associate/cpf/{cpf}", cpf))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(associateResponseDTO)));
    }

    @Test
    @DisplayName("shouldFindAllAssociatesSuccessfully")
    @SqlGroup({
            @Sql(scripts = "/test-scripts/setup-associate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/test-scripts/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void shouldFindAllAssociatesSuccessfully() throws Exception {
        mockMvc.perform(get("/api/v1/associate/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(associateResponseDTO))));
    }
}
