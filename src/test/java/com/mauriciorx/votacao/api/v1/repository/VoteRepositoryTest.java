package com.mauriciorx.votacao.api.v1.repository;

import com.mauriciorx.votacao.api.v1.entity.Agenda;
import com.mauriciorx.votacao.api.v1.entity.Associate;
import com.mauriciorx.votacao.api.v1.entity.Session;
import com.mauriciorx.votacao.api.v1.entity.Vote;
import com.mauriciorx.votacao.api.v1.enums.VoteEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Test
    void shouldSaveAndFindVote() {
        Agenda agenda = Agenda.builder()
                            .name("Pauta X")
                            .description("Descrição da pauta X")
                            .build();

        Agenda savedAgenda = agendaRepository.save(agenda);

        Session session = Session.builder().agenda(savedAgenda).build();
        session = sessionRepository.save(session);

        Associate associate = Associate.builder().name("Nome X").cpf("241.690.100-19").build();
        associate = associateRepository.save(associate);

        Vote vote = Vote.builder().session(session).associate(associate).vote(VoteEnum.YES).build();
        vote = voteRepository.save(vote);

        Optional<Vote> foundVote = voteRepository.findById(vote.getId());

        assertThat(foundVote).isPresent();
        assertThat(foundVote.get().getVote()).isEqualTo(VoteEnum.YES);
    }

    @Test
    void shouldFindByAssociateId() {
        Agenda agenda = Agenda.builder()
                            .name("Pauta X")
                            .description("Descrição da pauta X")
                            .build();

        Agenda savedAgenda = agendaRepository.save(agenda);

        Session session = Session.builder().agenda(savedAgenda).build();
        session = sessionRepository.save(session);

        Associate associate = Associate.builder().name("Nome X").cpf("241.690.100-19").build();
        associate = associateRepository.save(associate);

        Vote vote = Vote.builder().session(session).associate(associate).vote(VoteEnum.YES).build();
        vote = voteRepository.save(vote);

        List<Vote> foundVotes = voteRepository.findByAssociateId(associate.getId());

        assertThat(foundVotes).isNotEmpty();
        assertThat(foundVotes.get(0).getVote()).isEqualTo(VoteEnum.YES);
    }
}
