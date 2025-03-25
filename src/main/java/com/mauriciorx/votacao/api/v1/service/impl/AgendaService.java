package com.mauriciorx.votacao.api.v1.service.impl;

import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AgendaResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Agenda;
import com.mauriciorx.votacao.api.v1.repository.AgendaRepository;
import com.mauriciorx.votacao.api.v1.service.IAgendaService;
import com.mauriciorx.votacao.exception.AgendaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaService implements IAgendaService {

    @Autowired
    private AgendaRepository repository;

    @Override
    public AgendaResponseDTO create(AgendaRequestDTO agendaRequestDTO) {
        Agenda agenda = repository.save(Agenda.builder()
                                                    .name(agendaRequestDTO.getName())
                                                    .description(agendaRequestDTO.getDescription())
                                                    .build());
        return getAgendaResponseDTO( agenda );
    }

    @Override
    public AgendaResponseDTO findById(Long id) {
        return getAgendaResponseDTO(repository.findById(id)
                .orElseThrow(() -> new AgendaNotFoundException(id)));
    }

    @Override
    public List<AgendaResponseDTO> findAll() {
        return repository.findAll().stream().map(agenda -> getAgendaResponseDTO( agenda ) ).toList();
    }

    private AgendaResponseDTO getAgendaResponseDTO(Agenda agenda) {
        return AgendaResponseDTO.builder()
                                .id(agenda.getId())
                                .name(agenda.getName())
                                .description(agenda.getDescription())
                                .build();
    }
}
