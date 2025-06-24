package com.velhaguarda.dlemma.controller;

import com.velhaguarda.dlemma.dto.PandoraDilemmaResponseDTO;
import com.velhaguarda.dlemma.service.PandoraBoxService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pandora")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PandoraBoxController {

    private final PandoraBoxService pandoraBoxService;

    @Operation(summary = "Sortear dilema da Caixa de Pandora", 
               description = "Retorna um dilema aleatório da Caixa de Pandora. " +
                            "Apenas professores podem acessar este endpoint.")
    @PreAuthorize("hasRole('PROFESSOR')")
    @GetMapping("/random")
    public ResponseEntity<PandoraDilemmaResponseDTO> getRandomDilemma() {
        return ResponseEntity.ok(pandoraBoxService.getRandomDilemma());
    }
}