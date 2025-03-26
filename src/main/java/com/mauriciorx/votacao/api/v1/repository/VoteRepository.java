package com.mauriciorx.votacao.api.v1.repository;

import com.mauriciorx.votacao.api.v1.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findBySessionIdAndAssociateId(Long sessionId, Long associateId);

    @Query("""
        SELECT COUNT(v) FROM Vote v
        WHERE v.session.agenda.id = :agendaId AND v.vote = 'YES'
    """)
    Long countApprovedVotes(@Param("agendaId") Long agendaId);

    @Query("""
        SELECT COUNT(v) FROM Vote v
        WHERE v.session.agenda.id = :agendaId AND v.vote = 'NO'
    """)
    Long countRejectedVotes(@Param("agendaId") Long agendaId);
}
