package com.mauriciorx.votacao.api.v1.service;

import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.SessionResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteResponseDTO;

import java.util.List;

public interface ISessionService {

    VoteResponseDTO vote(VoteRequestDTO requestDTO);

    SessionResponseDTO create(SessionRequestDTO requestDTO);

    SessionResponseDTO findById(Long id);

    List<SessionResponseDTO> findAll();
}
