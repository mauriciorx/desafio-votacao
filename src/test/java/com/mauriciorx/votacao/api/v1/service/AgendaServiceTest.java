package com.mauriciorx.votacao.api.v1.service;

import com.mauriciorx.votacao.api.v1.dto.request.AgendaRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AgendaResponseDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteOutcomeResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Agenda;
import com.mauriciorx.votacao.api.v1.repository.AgendaRepository;
import com.mauriciorx.votacao.api.v1.repository.VoteRepository;
import com.mauriciorx.votacao.api.v1.service.impl.AgendaService;
import com.mauriciorx.votacao.exception.AgendaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @InjectMocks
    private AgendaService agendaService;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private VoteRepository voteRepository;

    private Agenda agenda;

    @BeforeEach
    void setUp() {
        agenda = Agenda.builder()
                .id(1L)
                .name("Nova Pauta")
                .description("Descrição da pauta")
                .build();
    }

    @Test
    void shouldCreateAgendaSuccessfully() {
        when(agendaRepository.save(any(Agenda.class))).thenReturn(agenda);

        AgendaRequestDTO requestDTO = new AgendaRequestDTO("Nova Pauta", "Descrição da pauta");
        AgendaResponseDTO responseDTO = agendaService.create(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(agenda.getId(), responseDTO.getId());
        assertEquals(agenda.getName(), responseDTO.getName());
        assertEquals(agenda.getDescription(), responseDTO.getDescription());

        verify(agendaRepository, times(1)).save(any(Agenda.class));
    }

    @Test
    void shouldFindAgendaByIdSuccessfully() {
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agenda));

        AgendaResponseDTO responseDTO = agendaService.findById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
    }

    @Test
    void shouldThrowExceptionWhenAgendaNotFound() {
        when(agendaRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(AgendaNotFoundException.class, () -> agendaService.findById(2L));
    }

    @Test
    void shouldGenerateVoteOutcomeSuccessfully() {
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agenda));
        when(voteRepository.countApprovedVotes(1L)).thenReturn(10L);
        when(voteRepository.countRejectedVotes(1L)).thenReturn(5L);

        VoteOutcomeResponseDTO outcomeDTO = agendaService.generateVoteOutcome(1L);

        assertNotNull(outcomeDTO);
        assertEquals("APROVADO", outcomeDTO.getOutcome());
        assertEquals(15, outcomeDTO.getOverall());
    }

    @Test
    void shouldReturnNoOutcomeWhenNoVotes() {
        when(agendaRepository.findById(1L)).thenReturn(Optional.of(agenda));
        when(voteRepository.countApprovedVotes(1L)).thenReturn(0L);
        when(voteRepository.countRejectedVotes(1L)).thenReturn(0L);

        VoteOutcomeResponseDTO outcomeDTO = agendaService.generateVoteOutcome(1L);

        assertNotNull(outcomeDTO);
        assertEquals("SEM RESULTADO", outcomeDTO.getOutcome());
    }
}
