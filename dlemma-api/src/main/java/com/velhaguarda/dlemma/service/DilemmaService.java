package com.velhaguarda.dlemma.service;

import com.velhaguarda.dlemma.dto.DilemmaRequestDTO;
import com.velhaguarda.dlemma.dto.DilemmaResponseDTO;
import com.velhaguarda.dlemma.dto.DilemmaStatusResponseDTO;
import com.velhaguarda.dlemma.entity.Dilemma;
import com.velhaguarda.dlemma.entity.User;
import com.velhaguarda.dlemma.entity.UsersChats;
import com.velhaguarda.dlemma.enums.InvitationStatus;
import com.velhaguarda.dlemma.repository.DilemmaRepository;
import com.velhaguarda.dlemma.repository.UserRepository;
import com.velhaguarda.dlemma.repository.UsersChatsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DilemmaService {

    private final DilemmaRepository dilemmaRepository;
    // private final DilemmaMapper dilemmaMapper;
    private final UserRepository userRepository;
    private final UsersChatsRepository usersChatsRepository;

    public DilemmaResponseDTO create(DilemmaRequestDTO dto) {
        Dilemma dilemma = new Dilemma();
        dilemma.setTitle(dto.getTitle());
        dilemma.setProfessorId(dto.getProfessorId());

        Dilemma saved = dilemmaRepository.save(dilemma);

        // Adiciona o professor ao chat como participante aceito
        User professor = userRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        UsersChats uc = new UsersChats();
        uc.setUser(professor);
        uc.setDilemma(saved);
        uc.setInvitation(InvitationStatus.ACCEPTED);
        uc.setScore(0);

        usersChatsRepository.save(uc);

        return new DilemmaResponseDTO(
                saved.getIdDilemma(),
                saved.getTitle(),
                saved.getProfessorId(),
                saved.getCreatedAt(),
                saved.getIsClosed(),
                saved.getClosedAt());
    }

    /*
     * public List<DilemmaResponseDTO> getDilemmasByUser(UUID userId) {
     * List<Dilemma> dilemmas = dilemmaRepository.findByUserId(userId);
     * return dilemmas.stream().map(dilemmaMapper::toResponseDTO).toList();
     * }
     */
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

       /**
     * Fecha um dilema (marca isClosed=true e closedAt=now).
     */
    @Transactional
    public DilemmaResponseDTO closeDilemma(int idDilemma) {
        Dilemma d = dilemmaRepository.findById(idDilemma)
            .orElseThrow(() -> new RuntimeException("Dilemma não encontrado: " + idDilemma));

        if (Boolean.TRUE.equals(d.getIsClosed())) {
            throw new RuntimeException("Dilemma já está encerrado: " + idDilemma);
        }

        d.setIsClosed(true);
        d.setClosedAt(LocalDateTime.now());
        Dilemma updated = dilemmaRepository.save(d);

        return new DilemmaResponseDTO(
            updated.getIdDilemma(),
            updated.getTitle(),
            updated.getProfessorId(),
            updated.getCreatedAt(),
            updated.getIsClosed(),
            updated.getClosedAt()
        );
    }

        public DilemmaResponseDTO getDilemmaById(int idDilemma) {
        Dilemma d = dilemmaRepository.findById(idDilemma)
            .orElseThrow(() -> new RuntimeException("Dilemma não encontrado: " + idDilemma));
        return new DilemmaResponseDTO(
            d.getIdDilemma(),
            d.getTitle(),
            d.getProfessorId(),
            d.getCreatedAt(),
            d.getIsClosed(),
            d.getClosedAt()
        );
    }

}
