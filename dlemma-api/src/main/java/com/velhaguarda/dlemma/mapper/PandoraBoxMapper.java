package com.velhaguarda.dlemma.mapper;

import com.velhaguarda.dlemma.dto.PandoraDilemmaResponseDTO;
import com.velhaguarda.dlemma.entity.PandoraBox;
import org.springframework.stereotype.Component;

@Component
public class PandoraBoxMapper {

    public PandoraDilemmaResponseDTO toResponseDTO(PandoraBox pandoraBox) {
        if (pandoraBox == null) return null;

        return PandoraDilemmaResponseDTO.builder()
                .id(pandoraBox.getId())
                .dilemmaTitle(pandoraBox.getDilemmaTitle())
                .professorId(pandoraBox.getProfessorId())
                .createdAt(pandoraBox.getCreatedAt())
                .build();
    }
}
