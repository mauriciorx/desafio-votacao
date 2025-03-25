package com.mauriciorx.votacao.api.v1.repository;

import com.mauriciorx.votacao.api.v1.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByAgendaId(Long agendaId);
}
