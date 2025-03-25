package com.mauriciorx.votacao.api.v1.service;

import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.SessionResponseDTO;

import java.util.List;

public interface ISessionService {

    SessionResponseDTO create(Long agendaId, SessionRequestDTO requestDTO);

    SessionResponseDTO findById(Long id);

    List<SessionResponseDTO> findAll();
}
