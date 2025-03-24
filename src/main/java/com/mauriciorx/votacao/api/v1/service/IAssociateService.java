package com.mauriciorx.votacao.api.v1.service;

import com.mauriciorx.votacao.api.v1.dto.request.AssociateRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AssociateResponseDTO;

import java.util.List;

public interface IAssociateService {

    AssociateResponseDTO create(AssociateRequestDTO requestDTO);

    AssociateResponseDTO findById(Long id);

    AssociateResponseDTO findByCpf(String cpf);

    List<AssociateResponseDTO> findAll();

}
