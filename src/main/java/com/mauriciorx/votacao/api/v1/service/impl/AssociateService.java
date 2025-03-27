package com.mauriciorx.votacao.api.v1.service.impl;

import com.mauriciorx.votacao.api.v1.dto.request.AssociateRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AssociateResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Associate;
import com.mauriciorx.votacao.api.v1.repository.AssociateRepository;
import com.mauriciorx.votacao.api.v1.service.IAssociateService;
import com.mauriciorx.votacao.client.exception.InvalidCpfException;
import com.mauriciorx.votacao.exception.AssociateNotFoundException;
import com.mauriciorx.votacao.exception.ExistentCpfException;
import com.mauriciorx.votacao.util.CpfUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssociateService implements IAssociateService {

    private final AssociateRepository repository;

    @Override
    public AssociateResponseDTO create(AssociateRequestDTO requestDTO) {
        log.info("Criando novo associado: {}", requestDTO);

        Optional<Associate> associateOptional = repository.findByCpf(requestDTO.getCpf());

        if(associateOptional.isPresent()) {
            log.warn("Tentativa de criação com CPF já existente: {}", requestDTO.getCpf());
            throw new ExistentCpfException(requestDTO.getCpf());
        }

        if(!CpfUtil.isCpfValid(requestDTO.getCpf())) {
            log.warn("Tentativa de criação com CPF inválido: {}", requestDTO.getCpf());
            throw new InvalidCpfException();
        }

        Associate associate = repository.save(Associate.builder()
                                                         .name(requestDTO.getName())
                                                         .cpf(requestDTO.getCpf())
                                                         .build());

        log.info("Associado criado com sucesso: {}", associate);

        return getAssociateResponseDTO(associate);
    }

    @Override
    public AssociateResponseDTO findById(Long id) {
        log.info("Buscando associado por ID: {}", id);

        return getAssociateResponseDTO(repository.findById(id)
                                                .orElseThrow(() -> {
                                                    log.warn("Associado não encontrado para ID: {}", id);
                                                    return new AssociateNotFoundException(id);
                                                }));
    }

    @Override
    public AssociateResponseDTO findByCpf(String cpf) {
        log.info("Buscando associado por CPF: {}", cpf);

        if(!CpfUtil.isCpfValid(cpf)){
            log.warn("Tentativa de busca com CPF inválido: {}", cpf);
            throw new InvalidCpfException();
        }

        return getAssociateResponseDTO(repository.findByCpf(cpf)
                                                .orElseThrow(() -> {
                                                    log.warn("Associado não encontrado para CPF: {}", cpf);
                                                    return new AssociateNotFoundException("Não foi encontrado um associado com o cpf: " + cpf);
                                                }));
    }

    @Override
    public List<AssociateResponseDTO> findAll() {
        log.info("Buscando todos os associados");

        List<Associate> associates = repository.findAll();

        log.info("{} associados encontrados", associates.size());

        return associates.stream().map(this::getAssociateResponseDTO).toList();
    }

    private AssociateResponseDTO getAssociateResponseDTO(Associate associate) {
        return AssociateResponseDTO.builder()
                                    .id(associate.getId())
                                    .name(associate.getName())
                                    .cpf(associate.getCpf()).build();
    }
}
