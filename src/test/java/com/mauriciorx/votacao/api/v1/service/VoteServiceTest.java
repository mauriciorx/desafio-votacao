package com.mauriciorx.votacao.api.v1.service;

import com.mauriciorx.votacao.api.v1.dto.request.VoteRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.VoteResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Associate;
import com.mauriciorx.votacao.api.v1.entity.Session;
import com.mauriciorx.votacao.api.v1.entity.Vote;
import com.mauriciorx.votacao.api.v1.enums.VoteEnum;
import com.mauriciorx.votacao.api.v1.repository.AssociateRepository;
import com.mauriciorx.votacao.api.v1.repository.SessionRepository;
import com.mauriciorx.votacao.api.v1.repository.VoteRepository;
import com.mauriciorx.votacao.api.v1.service.impl.VoteService;
import com.mauriciorx.votacao.exception.AlreadyVotedException;
import com.mauriciorx.votacao.exception.AssociateNotFoundException;
import com.mauriciorx.votacao.exception.SessionNotFoundException;
import com.mauriciorx.votacao.exception.VoteNotFoundException;
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
public class VoteServiceTest {

    @InjectMocks
    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private AssociateRepository associateRepository;

    private VoteRequestDTO voteRequestDTO;
    private Session session;
    private Associate associate;
    private Vote vote;

    @BeforeEach
    void setUp() {
        voteRequestDTO = new VoteRequestDTO(1L, 1L, true);
        session = new Session();
        session.setId(1L);
        associate = new Associate();
        associate.setId(1L);
        vote = new Vote(1L, session, associate, VoteEnum.YES);
    }

    @Test
    void shouldCreateVoteSuccessfully() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));
        when(voteRepository.findBySessionIdAndAssociateId(1L, 1L)).thenReturn(Optional.empty());
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        VoteResponseDTO response = voteService.create(voteRequestDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(VoteEnum.YES.getValue(), response.getVoteResult());

        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    void shouldThrowExceptionWhenSessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SessionNotFoundException.class, () -> voteService.create(voteRequestDTO));
    }

    @Test
    void shouldThrowExceptionWhenAssociateNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(associateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AssociateNotFoundException.class, () -> voteService.create(voteRequestDTO));
    }

    @Test
    void shouldThrowExceptionWhenAssociateAlreadyVoted() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));
        when(voteRepository.findBySessionIdAndAssociateId(1L, 1L)).thenReturn(Optional.of(vote));

        assertThrows(AlreadyVotedException.class, () -> voteService.create(voteRequestDTO));
    }

    @Test
    void shouldFindVoteById() {
        when(voteRepository.findById(1L)).thenReturn(Optional.of(vote));

        VoteResponseDTO response = voteService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(VoteEnum.YES.getValue(), response.getVoteResult());

        verify(voteRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenVoteNotFound() {
        when(voteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VoteNotFoundException.class, () -> voteService.findById(1L));
    }

    @Test
    void shouldFindAllVotes() {
        when(voteRepository.findAll()).thenReturn(List.of(vote));

        List<VoteResponseDTO> votes = voteService.findAll();

        assertFalse(votes.isEmpty());
        assertEquals(1, votes.size());
        assertEquals(VoteEnum.YES.getValue(), votes.get(0).getVoteResult());

        verify(voteRepository, times(1)).findAll();
    }
}