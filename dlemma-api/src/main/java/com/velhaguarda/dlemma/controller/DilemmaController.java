package com.velhaguarda.dlemma.controller;

import com.velhaguarda.dlemma.dto.DilemmaRequestDTO;
import com.velhaguarda.dlemma.dto.DilemmaResponseDTO;
import com.velhaguarda.dlemma.dto.DilemmaStatusResponseDTO;
import com.velhaguarda.dlemma.security.CustomUserDetails;
import com.velhaguarda.dlemma.service.DilemmaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/dilemmas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DilemmaController {
    private final DilemmaService dilemmaService;

    @Operation(summary = "Criar novo dilema", description = "Cria um dilema e vincula o professor automaticamente ao chat.")
    @PostMapping
    public ResponseEntity<DilemmaResponseDTO> create(@RequestBody @Valid DilemmaRequestDTO dto) {
        return ResponseEntity.status(201).body(dilemmaService.create(dto));
    }

    @Operation(summary = "Listar dilemmas", 
               description = "Lista os dilemmas que o usuário está inserido.")
    @GetMapping("/me")
    public List<DilemmaStatusResponseDTO> getMyDilemmas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UUID userId = userDetails.getUser().getId();
        return dilemmaService.getMyDilemmasWithStatus(userId);
    }

    @Operation(
      summary = "Encerrar um dilema",
      description = "Marca o dilema como encerrado (isClosed=true e closedAt=data atual)."
    )
    @PutMapping("/{id}/close")
    public ResponseEntity<DilemmaResponseDTO> close(@PathVariable("id") int id) {
        DilemmaResponseDTO dto = dilemmaService.closeDilemma(id);
        return ResponseEntity.ok(dto);
    }

@Operation(
    summary = "Buscar dilema por id",
    description = "Retorna todos os campos de um dilema específico, incluindo isClosed.")
    @GetMapping("/{id}")
    public ResponseEntity<DilemmaResponseDTO> getById(@PathVariable("id") int id) {
        DilemmaResponseDTO dto = dilemmaService.getDilemmaById(id);
        return ResponseEntity.ok(dto);
    }
}
