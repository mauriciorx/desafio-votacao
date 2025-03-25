package com.mauriciorx.votacao.api.v1.service;

import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AgendaResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VotingOutcomeResponseDTO;

import java.util.List;

public interface IAgendaService {

    AgendaResponseDTO create(AgendaRequestDTO agendaRequestDTO);

    AgendaResponseDTO findById(Long id);

    List<AgendaResponseDTO> findAll();
}
