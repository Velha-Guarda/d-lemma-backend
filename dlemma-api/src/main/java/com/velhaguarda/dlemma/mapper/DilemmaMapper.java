package com.velhaguarda.dlemma.mapper;

import org.springframework.stereotype.Component;

import com.velhaguarda.dlemma.dto.DilemmaRequestDTO;
import com.velhaguarda.dlemma.dto.DilemmaResponseDTO;
import com.velhaguarda.dlemma.entity.Dilemma;

@Component
public class DilemmaMapper {

    public Dilemma toEntity(DilemmaRequestDTO dto) {
        Dilemma dilemma = new Dilemma();
        dilemma.setTitle(dto.getTitle());
        dilemma.setProfessorId(dto.getProfessorId());
        return dilemma;
    }

    public DilemmaResponseDTO toResponseDTO(Dilemma entity) {
        return new DilemmaResponseDTO(
            entity.getIdDilemma(),
            entity.getTitle(),
            entity.getProfessorId(),
            entity.getCreatedAt(),
            entity.getIsClosed(),
            entity.getClosedAt()
        );
    }
}
