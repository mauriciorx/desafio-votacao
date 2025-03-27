package com.mauriciorx.votacao.api.v1.service;

import com.mauriciorx.votacao.api.v1.dto.request.SessionRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AgendaResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.SessionResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Agenda;
import com.mauriciorx.votacao.api.v1.entity.Session;
import com.mauriciorx.votacao.api.v1.enums.VoteEnum;
import com.mauriciorx.votacao.api.v1.repository.SessionRepository;
import com.mauriciorx.votacao.api.v1.service.impl.SessionService;
import com.mauriciorx.votacao.exception.SessionClosedException;
import com.mauriciorx.votacao.exception.SessionInProgressException;
import com.mauriciorx.votacao.exception.SessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private IAgendaService agendaService;

    @Mock
    private IVoteService voteService;

    private final Integer time = 120;

    private Session session;
    private Agenda agenda;

    @BeforeEach
    void setUp() {
        agenda = Agenda.builder()
                .id(1L)
                .name("Pauta 1")
                .description("Descrição da pauta 1")
                .build();

        session = Session.builder()
                .id(1L)
                .agenda(agenda)
                .time(time)
                .creationDate(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCreateSessionSuccessfully() {
        SessionRequestDTO requestDTO = new SessionRequestDTO(time, 1L, LocalDateTime.now());
        AgendaResponseDTO agendaResponseDTO = new AgendaResponseDTO(1L, "Pauta 1", "Descrição da pauta 1");

        when(agendaService.findById(1L)).thenReturn(agendaResponseDTO);
        when(sessionRepository.findByAgendaId(1L)).thenReturn(List.of());
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        SessionResponseDTO responseDTO = sessionService.create(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getAgendaId());
        assertEquals(time, responseDTO.getTime());

        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    void shouldThrowExceptionWhenAgendaAlreadyHasOpenSession() {
        SessionRequestDTO requestDTO = new SessionRequestDTO(time, 1L, session.getCreationDate());
        AgendaResponseDTO agendaResponseDTO = new AgendaResponseDTO(1L, "Pauta 1", "Descrição da pauta 1");

        when(agendaService.findById(1L)).thenReturn(agendaResponseDTO);
        when(sessionRepository.findByAgendaId(1L)).thenReturn(List.of(session));

        assertThrows(SessionInProgressException.class, () -> sessionService.create(requestDTO));

        verify(sessionRepository, never()).save(any(Session.class));
    }

    @Test
    void shouldFindSessionByIdSuccessfully() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        SessionResponseDTO responseDTO = sessionService.findById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
    }

    @Test
    void shouldThrowExceptionWhenSessionNotFound() {
        when(sessionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(SessionNotFoundException.class, () -> sessionService.findById(99L));
    }

    @Test
    void shouldVoteSuccessfullyWhenSessionIsOpen() {
        VoteRequestDTO voteRequestDTO = new VoteRequestDTO(1L, 1L, true);
        VoteResponseDTO voteResponseDTO = new VoteResponseDTO(1L, 1L, 1L, VoteEnum.YES.getValue());

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(voteService.create(voteRequestDTO)).thenReturn(voteResponseDTO);

        VoteResponseDTO response = sessionService.vote(voteRequestDTO);

        assertNotNull(response);
        assertEquals(VoteEnum.YES.getValue(), response.getVoteResult());

        verify(voteService, times(1)).create(voteRequestDTO);
    }

    @Test
    void shouldThrowExceptionWhenVotingInClosedSession() {
        session = Session.builder()
                        .id(1L)
                        .agenda(agenda)
                        .time(time)
                        .creationDate(LocalDateTime.now().minusMinutes(130))
                        .build();

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        VoteRequestDTO voteRequestDTO = new VoteRequestDTO(1L, 1L, true);

        assertThrows(SessionClosedException.class, () -> sessionService.vote(voteRequestDTO));

        verify(voteService, never()).create(any());
    }

    @Test
    void shouldFindAllSessionsSuccessfully() {
        when(sessionRepository.findAll()).thenReturn(List.of(session));

        List<SessionResponseDTO> sessions = sessionService.findAll();

        assertEquals(1, sessions.size());
        verify(sessionRepository, times(1)).findAll();
    }
}
