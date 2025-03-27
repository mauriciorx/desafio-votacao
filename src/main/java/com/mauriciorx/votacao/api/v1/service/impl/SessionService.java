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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    private final SessionRepository sessionRepository;

    private final IAgendaService agendaService;

    private final IVoteService voteService;

    @Override
    public VoteResponseDTO vote(VoteRequestDTO requestDTO) {
        log.info("Recebendo solicitação de voto: {}", requestDTO);

        Session session = sessionRepository.findById(requestDTO.getSessionId())
                                            .orElseThrow(() -> {
                                                log.warn("Sessão não encontrada para ID: {}", requestDTO.getSessionId());
                                                return new SessionNotFoundException(requestDTO.getSessionId());
                                            });

        if (!isSessionOpened(session)) {
            log.warn("Tentativa de voto em uma sessão encerrada: {}", session.getId());
            throw new SessionClosedException();
        }

        log.info("Voto registrado com sucesso para a sessão ID: {}", session.getId());

        return voteService.create(requestDTO);
    }

    @Override
    public SessionResponseDTO create(SessionRequestDTO requestDTO) {
        log.info("Criando nova sessão para pauta ID: {}", requestDTO.getAgendaId());

        AgendaResponseDTO agenda = agendaService.findById(requestDTO.getAgendaId());

        boolean anyOpenSession = sessionRepository.findByAgendaId( agenda.getId() ).stream().anyMatch( session -> isSessionOpened(session) );

        if (anyOpenSession) {
            log.warn("Já existe uma sessão em andamento para a pauta ID: {}", agenda.getId());
            throw new SessionInProgressException();
        }

        Session session = sessionRepository.save(Session.builder()
                                                        .time(requestDTO.getTime() != null ? requestDTO.getTime() : 60)
                                                        .agenda(Agenda.builder()
                                                                    .id(agenda.getId())
                                                                    .name(agenda.getName())
                                                                    .description(agenda.getDescription())
                                                                    .build())
                                                        .build());

        log.info("Sessão criada com sucesso: {}", session.getId());
        return getSessionResponseDTO(session);
    }

    @Override
    public SessionResponseDTO findById(Long id) {
        log.info("Buscando sessão por ID: {}", id);

        Session session = sessionRepository.findById(id)
                                            .orElseThrow(() -> {
                                                log.warn("Sessão não encontrada para ID: {}", id);
                                                return new SessionNotFoundException(id);
                                            });

        log.info("Sessão encontrada: {}", session);
        return getSessionResponseDTO(session);
    }

    @Override
    public List<SessionResponseDTO> findAll() {
        log.info("Buscando todas as sessões");

        List<Session> sessions = sessionRepository.findAll();

        log.info("{} sessões encontradas", sessions.size());

        return sessions.stream().map(this::getSessionResponseDTO).toList();    }

    private SessionResponseDTO getSessionResponseDTO(Session session) {
        return SessionResponseDTO.builder()
                                .id(session.getId())
                                .time(session.getTime())
                                .agendaId(session.getAgenda().getId())
                                .creationDate(session.getCreationDate())
                                .build();
    }

    private boolean isSessionOpened( Session session ) {
        LocalDateTime closeTime = session.getCreationDate().plusSeconds(session.getTime());

        boolean isOpen = closeTime.isAfter(LocalDateTime.now());

        log.info("Verificando status da sessão ID: {}, Status: {}", session.getId(), isOpen ? "Aberta" : "Encerrada" );

        return isOpen;
    }
}
