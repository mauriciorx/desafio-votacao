package com.mauriciorx.votacao.api.v1.repository;

import com.mauriciorx.votacao.api.v1.entity.Agenda;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AgendaRepositoryTest {

    @Autowired
    private AgendaRepository agendaRepository;

    @Test
    void shouldSaveAndFindAgenda() {
        Agenda agenda = Agenda.builder()
                            .name("Pauta de Teste")
                            .description("Descrição de Teste")
                            .build();

        Agenda savedAgenda = agendaRepository.save(agenda);

        assertNotNull(savedAgenda.getId());

        Optional<Agenda> foundAgenda = agendaRepository.findById(savedAgenda.getId());

        assertTrue(foundAgenda.isPresent());
        assertEquals("Pauta de Teste", foundAgenda.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenAgendaNotFound() {
        Optional<Agenda> agenda = agendaRepository.findById(999L);
        assertFalse(agenda.isPresent());
    }
}
