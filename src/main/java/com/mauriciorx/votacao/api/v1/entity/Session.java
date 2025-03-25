package com.mauriciorx.votacao.api.v1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer time;

    @ManyToOne()
    @JoinColumn(nullable = false, name="agenda_id")
    private Agenda agenda;

    @CreationTimestamp
    private LocalDateTime creationDate;
}
