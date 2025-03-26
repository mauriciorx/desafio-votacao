package com.mauriciorx.votacao.api.v1.service.impl;

import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AssociateResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.SessionResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Associate;
import com.mauriciorx.votacao.api.v1.entity.Session;
import com.mauriciorx.votacao.api.v1.entity.Vote;
import com.mauriciorx.votacao.api.v1.enums.VoteEnum;
import com.mauriciorx.votacao.api.v1.repository.AssociateRepository;
import com.mauriciorx.votacao.api.v1.repository.SessionRepository;
import com.mauriciorx.votacao.api.v1.repository.VoteRepository;
import com.mauriciorx.votacao.api.v1.service.IAssociateService;
import com.mauriciorx.votacao.api.v1.service.ISessionService;
import com.mauriciorx.votacao.api.v1.service.IVoteService;
import com.mauriciorx.votacao.exception.AlreadyVotedException;
import com.mauriciorx.votacao.exception.AssociateNotFoundException;
import com.mauriciorx.votacao.exception.SessionNotFoundException;
import com.mauriciorx.votacao.exception.VoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService implements IVoteService {

    private final VoteRepository voteRepository;

    private final SessionRepository sessionRepository;

    private final AssociateRepository associateRepository;

    @Override
    public VoteResponseDTO create(VoteRequestDTO requestDTO) {
        Session session = sessionRepository.findById(requestDTO.getSessionId())
                                            .orElseThrow(() -> new SessionNotFoundException(requestDTO.getSessionId()));

        Associate associate = associateRepository.findById(requestDTO.getAssociateId())
                                                .orElseThrow(() -> new AssociateNotFoundException(requestDTO.getAssociateId()));

        if(voteRepository.findBySessionIdAndAssociateId(requestDTO.getSessionId(), requestDTO.getAssociateId()).isPresent()) throw new AlreadyVotedException();

        Vote vote = voteRepository.save(Vote.builder().session(session)
                                                        .associate(associate)
                                                        .vote(requestDTO.isVoteApproved() ? VoteEnum.YES :
                                                                                            VoteEnum.NO).build());

        return getVoteResponseDTO(vote);
    }

    @Override
    public VoteResponseDTO findById(Long id) {
        return getVoteResponseDTO(voteRepository.findById(id)
                .orElseThrow(() -> new VoteNotFoundException(id)));
    }

    @Override
    public List<VoteResponseDTO> findAll() {
        return voteRepository.findAll().stream().map(vote -> getVoteResponseDTO(vote) ).toList();
    }

    public VoteResponseDTO getVoteResponseDTO(Vote vote) {
        return VoteResponseDTO.builder()
                                .id(vote.getId())
                                .sessionId(vote.getSession().getId())
                                .associateId(vote.getAssociate().getId())
                                .voteResult(vote.getVote().getValue()).build();
    }
}
