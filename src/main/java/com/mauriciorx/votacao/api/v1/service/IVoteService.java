package com.mauriciorx.votacao.api.v1.service;

import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteResponseDTO;

import java.util.List;

public interface IVoteService {

    VoteResponseDTO create(VoteRequestDTO requestDTO);

    VoteResponseDTO findById(Long id);

    List<VoteResponseDTO> findAll();
}
