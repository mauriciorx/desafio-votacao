package com.mauriciorx.votacao.api.v1.service.impl;

import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Associate;
import com.mauriciorx.votacao.api.v1.entity.Session;
import com.mauriciorx.votacao.api.v1.entity.Vote;
import com.mauriciorx.votacao.api.v1.enums.VoteEnum;
import com.mauriciorx.votacao.api.v1.repository.AssociateRepository;
import com.mauriciorx.votacao.api.v1.repository.SessionRepository;
import com.mauriciorx.votacao.api.v1.repository.VoteRepository;
import com.mauriciorx.votacao.api.v1.service.IVoteService;
import com.mauriciorx.votacao.exception.AlreadyVotedException;
import com.mauriciorx.votacao.exception.AssociateNotFoundException;
import com.mauriciorx.votacao.exception.SessionNotFoundException;
import com.mauriciorx.votacao.exception.VoteNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteService implements IVoteService {

    private final VoteRepository voteRepository;

    private final SessionRepository sessionRepository;

    private final AssociateRepository associateRepository;

    @Override
    public VoteResponseDTO create(VoteRequestDTO requestDTO) {
        log.info("Iniciando criação do voto para associado {} na sessão {}", requestDTO.getAssociateId(), requestDTO.getSessionId());

        Session session = sessionRepository.findById(requestDTO.getSessionId())
                                                                .orElseThrow(() -> {
                                                                    log.warn("Sessão não encontrada para ID: {}", requestDTO.getSessionId());
                                                                    return new SessionNotFoundException(requestDTO.getSessionId());
                                                                });

        Associate associate = associateRepository.findById(requestDTO.getAssociateId())
                                                .orElseThrow(() -> {
                                                    log.warn("Associado não encontrado para ID: {}", requestDTO.getAssociateId());
                                                    return new AssociateNotFoundException(requestDTO.getAssociateId());
                                                });

        List<Vote> votesByAgenda = voteRepository.findByAssociateId( requestDTO.getAssociateId() ).stream()
                .filter( vote -> vote.getSession().getAgenda().getId() == session.getAgenda().getId() ).toList();

        if (!votesByAgenda.isEmpty()) {
            log.warn("Associado {} já votou nesta pauta", requestDTO.getAssociateId());
            throw new AlreadyVotedException();
        }

        Vote vote = voteRepository.save(Vote.builder().session(session)
                                                        .associate(associate)
                                                        .vote(requestDTO.isVoteApproved() ? VoteEnum.YES :
                                                                                            VoteEnum.NO).build());

        log.info("Voto registrado com sucesso para associado {} na sessão {}", requestDTO.getAssociateId(), requestDTO.getSessionId());
        return getVoteResponseDTO(vote);
    }

    @Override
    public VoteResponseDTO findById(Long id) {
        log.info("Buscando voto por ID: {}", id);

        return getVoteResponseDTO(voteRepository.findById(id)
                                                .orElseThrow(() -> {
                                                    log.warn("Voto não encontrado para ID: {}", id);
                                                    return new VoteNotFoundException(id);
                                                }));
    }

    @Override
    public List<VoteResponseDTO> findAll() {
        log.info("Buscando todos os votos cadastrados");

        List<Vote> votes = voteRepository.findAll();

        log.info("{} votos encontrados", votes.size());

        return votes.stream().map(this::getVoteResponseDTO).toList();
    }

    public VoteResponseDTO getVoteResponseDTO(Vote vote) {
        return VoteResponseDTO.builder()
                                .id(vote.getId())
                                .sessionId(vote.getSession().getId())
                                .associateId(vote.getAssociate().getId())
                                .voteResult(vote.getVote().getValue()).build();
    }
}
