package com.mauriciorx.votacao.api.v1.entity;

import com.mauriciorx.votacao.api.v1.enums.VoteEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(nullable = false, name="session_id")
    private Session session;

    @ManyToOne()
    @JoinColumn(nullable = false, name="associate_id")
    private Associate associate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteEnum vote;
}
