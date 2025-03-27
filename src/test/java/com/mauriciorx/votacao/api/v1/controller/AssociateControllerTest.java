package com.mauriciorx.votacao.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mauriciorx.votacao.api.v1.dto.request.AssociateRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AssociateResponseDTO;
import com.mauriciorx.votacao.api.v1.service.IAssociateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AssociateController.class)
public class AssociateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IAssociateService associateService;

    @Autowired
    private ObjectMapper objectMapper;

    private AssociateRequestDTO associateRequestDTO;
    private AssociateResponseDTO associateResponseDTO;

    private final String cpf = "241.690.100-19";

    @BeforeEach
    void setUp() {
        associateRequestDTO = new AssociateRequestDTO("Maurício Ribeiro Xavier", cpf);
        associateResponseDTO = new AssociateResponseDTO(1L, "Maurício Ribeiro Xavier", cpf);
    }

    @Test
    void testCreateAssociate() throws Exception {
        when(associateService.create(any(AssociateRequestDTO.class))).thenReturn(associateResponseDTO);

        mockMvc.perform(post("/api/v1/associate/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(associateRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(associateResponseDTO.getId()))
                .andExpect(jsonPath("$.name").value(associateResponseDTO.getName()))
                .andExpect(jsonPath("$.cpf").value(associateResponseDTO.getCpf()));
    }

    @Test
    void testFindById() throws Exception {
        when(associateService.findById(1L)).thenReturn(associateResponseDTO);

        mockMvc.perform(get("/api/v1/associate/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(associateResponseDTO.getId()));
    }

    @Test
    void testFindByCpf() throws Exception {
        when(associateService.findByCpf(cpf)).thenReturn(associateResponseDTO);

        mockMvc.perform(get("/api/v1/associate/cpf/" + cpf))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(associateResponseDTO.getCpf()));
    }

    @Test
    void testFindAll() throws Exception {
        when(associateService.findAll()).thenReturn(Collections.singletonList(associateResponseDTO));

        mockMvc.perform(get("/api/v1/associate/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(associateResponseDTO.getId()));
    }
}