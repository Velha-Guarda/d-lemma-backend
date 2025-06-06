package com.velhaguarda.dlemma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velhaguarda.dlemma.config.TestSecurityConfig;
import com.velhaguarda.dlemma.dto.DilemmaRequestDTO;
import com.velhaguarda.dlemma.dto.DilemmaResponseDTO;
import com.velhaguarda.dlemma.dto.DilemmaStatusResponseDTO;
import com.velhaguarda.dlemma.entity.User;
import com.velhaguarda.dlemma.security.CustomUserDetails;
import com.velhaguarda.dlemma.security.UserDetailsServiceImpl;
import com.velhaguarda.dlemma.service.DilemmaService;
import com.velhaguarda.dlemma.service.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(DilemmaController.class)
class DilemmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DilemmaService dilemmaService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private void authenticateAs(UUID userId, String role) {
        User user = new User();
        user.setId(userId);
        user.setEmail("teste@user.com");
        user.setPassword("123");

        CustomUserDetails userDetails = new CustomUserDetails(user, List.of());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }

    @Test
    void deveCriarDilemaComSucesso() throws Exception {
        DilemmaRequestDTO request = new DilemmaRequestDTO();
        request.setTitle("Novo dilema");
        request.setProfessorId(UUID.randomUUID());

        DilemmaResponseDTO response = new DilemmaResponseDTO(
                1, request.getTitle(), request.getProfessorId(), LocalDateTime.now(), false, null);

        when(dilemmaService.create(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/dilemmas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Novo dilema"));
    }

    @Test
    void deveRetornarDilemasDoUsuario() throws Exception {
        UUID userId = UUID.randomUUID();
        authenticateAs(userId, "ALUNO");

        List<DilemmaStatusResponseDTO> dilemmas = List.of(
                new DilemmaStatusResponseDTO(1, "Dilema 1", UUID.randomUUID(), "PENDING", false, null),
                new DilemmaStatusResponseDTO(2, "Dilema 2", UUID.randomUUID(), "ACCEPTED", true, LocalDateTime.now()));

        when(dilemmaService.getMyDilemmasWithStatus(userId)).thenReturn(dilemmas);

        mockMvc.perform(get("/dilemmas/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Dilema 1"));
    }

    @Test
    void deveRetornarErroDeValidacaoAoCriarDilemaSemTitulo() throws Exception {
        DilemmaRequestDTO request = new DilemmaRequestDTO();
        request.setProfessorId(UUID.randomUUID());

        mockMvc.perform(post("/dilemmas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarErro403ComRoleInvalida() throws Exception {
        // Usuário autenticado com ROLE errada
        SecurityContextHolder.clearContext(); // Simula ausência de permissões

        mockMvc.perform(get("/dilemmas/me"))
                .andExpect(status().isForbidden());
    }

    @Test
    void deveRetornarErro500SeServicoFalhar() throws Exception {
        UUID userId = UUID.randomUUID();
        authenticateAs(userId, "ALUNO");

        when(dilemmaService.getMyDilemmasWithStatus(userId))
                .thenThrow(new RuntimeException("Falha interna"));

        mockMvc.perform(get("/dilemmas/me"))
                .andExpect(status().isInternalServerError());
    }
}
