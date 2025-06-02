package com.velhaguarda.dlemma.controller;

import com.velhaguarda.dlemma.dto.DilemmaRequestDTO;
import com.velhaguarda.dlemma.dto.DilemmaResponseDTO;
import com.velhaguarda.dlemma.service.DilemmaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dilemmas")
@RequiredArgsConstructor
public class DilemmaController {

    private final DilemmaService dilemmaService;

    @PostMapping
    public ResponseEntity<DilemmaResponseDTO> create(@RequestBody @Valid DilemmaRequestDTO dto) {
        return ResponseEntity.status(201).body(dilemmaService.create(dto));
    }
}
