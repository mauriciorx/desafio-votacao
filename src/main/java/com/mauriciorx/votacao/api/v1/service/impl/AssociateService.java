package com.mauriciorx.votacao.api.v1.service.impl;

import com.mauriciorx.votacao.api.v1.entity.Associate;
import com.mauriciorx.votacao.api.v1.dto.request.AssociateRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AssociateResponseDTO;
import com.mauriciorx.votacao.api.v1.repository.AssociateRepository;
import com.mauriciorx.votacao.api.v1.service.IAssociateService;
import com.mauriciorx.votacao.client.exception.InvalidCpfException;
import com.mauriciorx.votacao.exception.ExistentCpfException;
import com.mauriciorx.votacao.exception.AssociateNotFoundException;
import com.mauriciorx.votacao.util.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociateService implements IAssociateService {

    @Autowired
    private AssociateRepository repository;

    @Override
    public AssociateResponseDTO create(AssociateRequestDTO requestDTO) {
        Optional<Associate> associateOptional = repository.findByCpf(requestDTO.getCpf());

        if(associateOptional.isPresent()) throw new ExistentCpfException(requestDTO.getCpf());

        if(!CpfUtil.isCpfValid(requestDTO.getCpf())) throw new InvalidCpfException();

        Associate associate = repository.save(Associate.builder()
                                                         .name(requestDTO.getName())
                                                         .cpf(requestDTO.getCpf())
                                                         .build());

        return getAssociateResponseDTO(associate);
    }

    @Override
    public AssociateResponseDTO findById(Long id) {
        return getAssociateResponseDTO(repository.findById(id)
                .orElseThrow(() -> new AssociateNotFoundException(id)));
    }

    @Override
    public AssociateResponseDTO findByCpf(String cpf) {
        if(!CpfUtil.isCpfValid(cpf)) throw new InvalidCpfException();

        return getAssociateResponseDTO(repository.findByCpf(cpf)
                .orElseThrow(() -> new AssociateNotFoundException("Não foi encontrado um associado com o cpf: " + cpf)));
    }

    @Override
    public List<AssociateResponseDTO> findAll() {
        return repository.findAll().stream().map(associate -> getAssociateResponseDTO( associate ) ).toList();
    }

    private AssociateResponseDTO getAssociateResponseDTO(Associate associate) {
        return AssociateResponseDTO.builder()
                                    .id(associate.getId())
                                    .name(associate.getName())
                                    .cpf(associate.getCpf()).build();
    }
}
