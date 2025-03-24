package com.mauriciorx.votacao.api.v1.repository;

import com.mauriciorx.votacao.api.v1.entity.Associate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociateRepository extends JpaRepository<Associate, Long> {
    Optional<Associate> findByCpf( String cpf );
}
