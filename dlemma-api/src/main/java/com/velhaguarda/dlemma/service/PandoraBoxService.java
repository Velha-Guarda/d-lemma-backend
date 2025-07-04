package com.velhaguarda.dlemma.service;

import com.velhaguarda.dlemma.dto.PandoraDilemmaResponseDTO;
import com.velhaguarda.dlemma.entity.PandoraBox;
import com.velhaguarda.dlemma.mapper.PandoraBoxMapper;
import com.velhaguarda.dlemma.repository.PandoraBoxRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PandoraBoxService {

    private final PandoraBoxRepository pandoraBoxRepository;
    private final PandoraBoxMapper pandoraBoxMapper;
public PandoraDilemmaResponseDTO getRandomDilemma() {
    try {
        List<PandoraBox> allDilemmas = pandoraBoxRepository.findAll();
        System.out.println(">> Total de dilemas encontrados: " + allDilemmas.size());

        if (allDilemmas.isEmpty()) {
            throw new RuntimeException("Nenhum dilema encontrado na Caixa de Pandora");
        }

        Random random = new Random();
        PandoraBox randomDilemma = allDilemmas.get(random.nextInt(allDilemmas.size()));
        System.out.println(">> Dilema sorteado: " + randomDilemma.getDilemmaTitle());
        System.out.println(">> ID Dilema sorteado: " + randomDilemma.getId());

        return pandoraBoxMapper.toResponseDTO(randomDilemma);
    } catch (Exception e) {
        System.err.println(">> ERRO EM getRandomDilemma: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
}
}