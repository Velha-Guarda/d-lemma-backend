package com.velhaguarda.dlemma.service;

import com.velhaguarda.dlemma.dto.DilemmaRequestDTO;
import com.velhaguarda.dlemma.dto.DilemmaResponseDTO;
import com.velhaguarda.dlemma.dto.DilemmaStatusResponseDTO;
import com.velhaguarda.dlemma.entity.Dilemma;
import com.velhaguarda.dlemma.entity.UsersChats;
import com.velhaguarda.dlemma.repository.DilemmaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DilemmaService {

    private final DilemmaRepository dilemmaRepository;
    //private final DilemmaMapper dilemmaMapper;

    public DilemmaResponseDTO create(DilemmaRequestDTO dto) {
        Dilemma dilemma = new Dilemma();
        dilemma.setTitle(dto.getTitle());
        dilemma.setProfessorId(dto.getProfessorId());

        Dilemma saved = dilemmaRepository.save(dilemma);

        return new DilemmaResponseDTO(
                saved.getIdDilemma(),
                saved.getTitle(),
                saved.getProfessorId(),
                saved.getCreatedAt(),
                saved.getIsClosed(),
                saved.getClosedAt());
    }

    /*public List<DilemmaResponseDTO> getDilemmasByUser(UUID userId) {
        List<Dilemma> dilemmas = dilemmaRepository.findByUserId(userId);
        return dilemmas.stream().map(dilemmaMapper::toResponseDTO).toList();
    }*/
    public List<DilemmaStatusResponseDTO> getMyDilemmasWithStatus(UUID userId) {
    List<UsersChats> participations = dilemmaRepository.findParticipationsByUserId(userId);
    return participations.stream().map(uc -> {
        DilemmaStatusResponseDTO dto = new DilemmaStatusResponseDTO();
        dto.setIdDilemma(uc.getDilemma().getIdDilemma());
        dto.setTitle(uc.getDilemma().getTitle());
        dto.setProfessorId(uc.getDilemma().getProfessorId());
        dto.setInvitationStatus(uc.getInvitation().name());
        dto.setIsClosed(uc.getDilemma().getIsClosed());
        dto.setClosedAt(uc.getDilemma().getClosedAt());
        return dto;
    }).toList();
}

    

}
