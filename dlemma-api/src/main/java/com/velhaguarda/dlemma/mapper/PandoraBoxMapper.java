package com.velhaguarda.dlemma.mapper;

import com.velhaguarda.dlemma.dto.PandoraDilemmaResponseDTO;
import com.velhaguarda.dlemma.entity.PandoraBox;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PandoraBoxMapper {
    PandoraBoxMapper INSTANCE = Mappers.getMapper(PandoraBoxMapper.class);

    PandoraDilemmaResponseDTO toResponseDTO(PandoraBox pandoraBox);
}