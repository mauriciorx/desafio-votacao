package com.mauriciorx.votacao.api.v1.service.impl;

import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AgendaResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteOutcomeResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Agenda;
import com.mauriciorx.votacao.api.v1.entity.Associate;
import com.mauriciorx.votacao.api.v1.repository.AgendaRepository;
import com.mauriciorx.votacao.api.v1.repository.VoteRepository;
import com.mauriciorx.votacao.api.v1.service.IAgendaService;
import com.mauriciorx.votacao.exception.AgendaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendaService implements IAgendaService {

    private final AgendaRepository agendaRepository;

    private final VoteRepository voteRepository;

    @Override
    public AgendaResponseDTO create(AgendaRequestDTO agendaRequestDTO) {
        log.info("Criando uma nova pauta: {}", agendaRequestDTO.getName());

        Agenda agenda = agendaRepository.save(Agenda.builder()
                                                    .name(agendaRequestDTO.getName())
                                                    .description(agendaRequestDTO.getDescription())
                                                    .build());

        log.info("Pauta criada com sucesso: {}", agenda);

        return getAgendaResponseDTO( agenda );
    }

    @Override
    public AgendaResponseDTO findById(Long id) {
        log.info("Buscando pauta por ID: {}", id);

        return getAgendaResponseDTO(agendaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Agenda não encontrada para ID: {}", id);
                    return new AgendaNotFoundException(id);
                }));
    }

    @Override
    public List<AgendaResponseDTO> findAll() {
        log.info("Buscando todas as pautas");

        List<Agenda> agendas = agendaRepository.findAll();

        log.info("{} pautas encontradas", agendas.size());

        return agendas.stream().map(this::getAgendaResponseDTO).toList();
    }

    @Override
    public VoteOutcomeResponseDTO generateVoteOutcome(Long agendaId) {
        log.info("Gerando resultado da votação para a pauta ID: {}", agendaId);

        Agenda agenda = agendaRepository.findById(agendaId)
                                        .orElseThrow(() -> {
                                            log.warn("Agenda não encontrada para ID: {}", agendaId);
                                            return new AgendaNotFoundException(agendaId);
                                        });

        Long approvedVotes = voteRepository.countApprovedVotes(agenda.getId());
        Long rejectedVotes = voteRepository.countRejectedVotes(agenda.getId());
        Long overall = approvedVotes + rejectedVotes;

        String outcome = overall == 0 ? "SEM RESULTADO" : approvedVotes > rejectedVotes ? "APROVADO" : "REJEITADO";

        log.info("Resultado gerado para a pauta '{}': Aprovados: {}, Rejeitados: {}, Total: {}, Resultado: {}",
                    agenda.getName(), approvedVotes, rejectedVotes, overall, outcome);

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
