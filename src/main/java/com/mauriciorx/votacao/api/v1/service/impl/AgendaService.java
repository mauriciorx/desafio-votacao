package com.mauriciorx.votacao.api.v1.service.impl;

import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AgendaResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteOutcomeResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Agenda;
import com.mauriciorx.votacao.api.v1.repository.AgendaRepository;
import com.mauriciorx.votacao.api.v1.repository.VoteRepository;
import com.mauriciorx.votacao.api.v1.service.IAgendaService;
import com.mauriciorx.votacao.exception.AgendaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService implements IAgendaService {

    private final AgendaRepository agendaRepository;

    private final VoteRepository voteRepository;

    @Override
    public AgendaResponseDTO create(AgendaRequestDTO agendaRequestDTO) {
        Agenda agenda = agendaRepository.save(Agenda.builder()
                                                    .name(agendaRequestDTO.getName())
                                                    .description(agendaRequestDTO.getDescription())
                                                    .build());
        return getAgendaResponseDTO( agenda );
    }

    @Override
    public AgendaResponseDTO findById(Long id) {
        return getAgendaResponseDTO(agendaRepository.findById(id)
                .orElseThrow(() -> new AgendaNotFoundException(id)));
    }

    @Override
    public List<AgendaResponseDTO> findAll() {
        return agendaRepository.findAll().stream().map(agenda -> getAgendaResponseDTO( agenda ) ).toList();
    }

    @Override
    public VoteOutcomeResponseDTO generateVoteOutcome(Long agendaId) {
        Agenda agenda = agendaRepository.findById(agendaId)
                            .orElseThrow(() -> new AgendaNotFoundException(agendaId));

        Long approvedVotes = voteRepository.countApprovedVotes(agenda.getId());
        Long rejectedVotes = voteRepository.countRejectedVotes(agenda.getId());
        Long overall = approvedVotes + rejectedVotes;

        String outcome = overall == 0 ? "SEM RESULTADO" : approvedVotes > rejectedVotes ? "APROVADO" : "REJEITADO";

        return VoteOutcomeResponseDTO.builder()
                .title(agenda.getName())
                .description(agenda.getDescription())
                .approvedVotes(approvedVotes)
                .rejectedVotes(rejectedVotes)
                .overall(overall)
                .outcome(outcome)
                .build();
    }

    private AgendaResponseDTO getAgendaResponseDTO(Agenda agenda) {
        return AgendaResponseDTO.builder()
                                .id(agenda.getId())
                                .name(agenda.getName())
                                .description(agenda.getDescription())
                                .build();
    }
}
