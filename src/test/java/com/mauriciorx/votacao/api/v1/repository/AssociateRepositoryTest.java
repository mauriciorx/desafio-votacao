package com.mauriciorx.votacao.api.v1.repository;

import com.mauriciorx.votacao.api.v1.entity.Associate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AssociateRepositoryTest {

    @Autowired
    private AssociateRepository associateRepository;

    private final String cpf = "241.690.100-19";

    @Test
    void shouldSaveAndFindAssociate() {
        Associate associate = Associate.builder()
                                        .name("Carlos Souza")
                                        .cpf(cpf)
                                        .build();

        Associate savedAssociate = associateRepository.save(associate);

        assertNotNull(savedAssociate.getId());

        Optional<Associate> foundAssociate = associateRepository.findById(savedAssociate.getId());

        assertTrue(foundAssociate.isPresent());
        assertEquals("Carlos Souza", foundAssociate.get().getName());
    }

    @Test
    void shouldFindAssociateByCpf() {
        Associate associate = Associate.builder()
                                        .name("Ana Lima")
                                        .cpf(cpf)
                                        .build();

        associateRepository.save(associate);

        Optional<Associate> foundAssociate = associateRepository.findByCpf(cpf);

        assertTrue(foundAssociate.isPresent());
        assertEquals("Ana Lima", foundAssociate.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenAssociateNotFound() {
        Optional<Associate> associate = associateRepository.findById(999L);
        assertFalse(associate.isPresent());
    }
}