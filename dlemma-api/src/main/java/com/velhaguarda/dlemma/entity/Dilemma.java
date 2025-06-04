package com.velhaguarda.dlemma.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "chats")
public class Dilemma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dilemma")
    private int idDilemma;

    @Column(name = "dilemma_title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "professor_id", nullable = false)
    private UUID professorId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "is_closed")
    private Boolean isClosed = false;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
}
