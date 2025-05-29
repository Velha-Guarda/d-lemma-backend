package com.velhaguarda.dlemma.service;

import com.velhaguarda.dlemma.dto.DilemmaRequestDTO;
import com.velhaguarda.dlemma.dto.DilemmaResponseDTO;
import com.velhaguarda.dlemma.entity.Dilemma;
import com.velhaguarda.dlemma.repository.DilemmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DilemmaService {

    private final DilemmaRepository repository;

    public DilemmaResponseDTO create(DilemmaRequestDTO dto) {

        Dilemma dilemma = new Dilemma();
        dilemma.setTitle(dto.getTitle());
        dilemma.setProfessorId(dto.getProfessorId());

        Dilemma saved = repository.save(dilemma);


        return new DilemmaResponseDTO(
                saved.getIdDilemma(),
                saved.getTitle(),
                saved.getProfessorId(),
                saved.getCreatedAt(),
                saved.getIsClosed(),
                saved.getClosedAt()
        );
    }
}
