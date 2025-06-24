package com.velhaguarda.dlemma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pandora_box")
public class PandoraBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dilemma_title", nullable = false)
    private String dilemmaTitle;

    @Column(name = "professor_id", nullable = false)
    private UUID professorId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}