package com.mauriciorx.votacao.api.v1.repository;

import com.mauriciorx.votacao.api.v1.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findBySessionIdAndAssociateId(Long sessionId, Long associateId);
}
