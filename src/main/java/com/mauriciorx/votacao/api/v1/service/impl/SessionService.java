package com.mauriciorx.votacao.api.v1.service.impl;

import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AgendaResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.SessionResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Agenda;
import com.mauriciorx.votacao.api.v1.entity.Session;
import com.mauriciorx.votacao.api.v1.repository.SessionRepository;
import com.mauriciorx.votacao.api.v1.service.IAgendaService;
import com.mauriciorx.votacao.api.v1.service.ISessionService;
import com.mauriciorx.votacao.api.v1.service.IVoteService;
import com.mauriciorx.votacao.exception.SessionClosedException;
import com.mauriciorx.votacao.exception.SessionInProgressException;
import com.mauriciorx.votacao.exception.SessionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;

    private final IAgendaService agendaService;

    private final IVoteService voteService;

    @Override
    public VoteResponseDTO vote(VoteRequestDTO requestDTO) {
        Session session = sessionRepository.findById(requestDTO.getSessionId())
                                            .orElseThrow(() -> new SessionNotFoundException(requestDTO.getSessionId()));

        if(!isSessionOpened(session)) throw new SessionClosedException();

        return voteService.create(requestDTO);
    }

    @Override
    public SessionResponseDTO create(SessionRequestDTO requestDTO) {
        AgendaResponseDTO agenda = agendaService.findById(requestDTO.getAgendaId());

        boolean anyOpenSession = sessionRepository.findByAgendaId( agenda.getId() ).stream().anyMatch( session -> isSessionOpened(session) );

        if( anyOpenSession ) throw new SessionInProgressException();

        Session session = sessionRepository.save(Session.builder()
                                                        .time(requestDTO.getTime() != null ? requestDTO.getTime() : 60)
                                                        .agenda(Agenda.builder()
                                                                    .id(agenda.getId())
                                                                    .name(agenda.getName())
                                                                    .description(agenda.getDescription())
                                                                    .build())
                                                        .build());

        return getSessionResponseDTO(session);
    }

    @Override
    public SessionResponseDTO findById(Long id) {
        return getSessionResponseDTO(sessionRepository.findById(id)
                .orElseThrow(() -> new SessionNotFoundException(id)));
    }

    @Override
    public List<SessionResponseDTO> findAll() {
        return sessionRepository.findAll().stream().map(session -> getSessionResponseDTO(session) ).toList();
    }

    private SessionResponseDTO getSessionResponseDTO(Session session) {
        return SessionResponseDTO.builder()
                                .id(session.getId())
                                .time(session.getTime())
                                .agendaId(session.getAgenda().getId())
                                .creationDate(session.getCreationDate())
                                .build();
    }

    private boolean isSessionOpened( Session session )
    {
        LocalDateTime closeTime = session.getCreationDate().plusSeconds(session.getTime());

        return closeTime.isAfter( LocalDateTime.now() );
    }
}
