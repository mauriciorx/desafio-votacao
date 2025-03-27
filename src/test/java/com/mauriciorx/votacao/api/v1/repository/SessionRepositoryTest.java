package com.mauriciorx.votacao.api.v1.repository;

import com.mauriciorx.votacao.api.v1.entity.Agenda;
import com.mauriciorx.votacao.api.v1.entity.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Test
    void shouldSaveAndFindSession() {
        Agenda agenda = Agenda.builder()
                            .name("Pauta X")
                            .description("Descrição da pauta X")
                            .build();

        Agenda savedAgenda = agendaRepository.save(agenda);

        Session session = Session.builder()
                                .agenda(savedAgenda)
                                .time(30)
                                .build();

        Session savedSession = sessionRepository.save(session);

        assertNotNull(savedSession.getId());

        Optional<Session> foundSession = sessionRepository.findById(savedSession.getId());

        assertTrue(foundSession.isPresent());
        assertEquals(30, foundSession.get().getTime());
    }

    @Test
    void shouldFindSessionByAgendaId() {
        Agenda agenda = Agenda.builder()
                                .name("Pauta Y")
                                .description("Descrição da pauta Y")
                                .build();

        Agenda savedAgenda = agendaRepository.save(agenda);

        Session session = Session.builder()
                                .agenda(savedAgenda)
                                .time(45)
                                .build();

        sessionRepository.save(session);

        List<Session> sessions = sessionRepository.findByAgendaId(savedAgenda.getId());

        assertFalse(sessions.isEmpty());
        assertEquals(45, sessions.get(0).getTime());
    }
}