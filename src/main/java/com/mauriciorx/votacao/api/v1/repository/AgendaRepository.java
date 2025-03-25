package com.mauriciorx.votacao.api.v1.repository;

import com.mauriciorx.votacao.api.v1.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}
