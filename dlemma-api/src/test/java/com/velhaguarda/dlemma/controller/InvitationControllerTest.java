package com.velhaguarda.dlemma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velhaguarda.dlemma.config.TestSecurityConfig;
import com.velhaguarda.dlemma.dto.InvitationResponseDTO;
import com.velhaguarda.dlemma.dto.InviteRequestDTO;
import com.velhaguarda.dlemma.enums.InvitationStatus;
import com.velhaguarda.dlemma.security.UserDetailsServiceImpl;
import com.velhaguarda.dlemma.service.InvitationService;
import com.velhaguarda.dlemma.service.JwtService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.Import;

@Import(TestSecurityConfig.class)
@WebMvcTest(controllers = InvitationController.class)
public class InvitationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvitationService invitationService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @WithMockUser(username = "professor", roles = { "PROFESSOR" })
    @Test
    void deveEnviarConviteComSucesso() throws Exception {
        InviteRequestDTO dto = new InviteRequestDTO("aluno@email.com", 1);

        mockMvc.perform(post("/invitations/invite")
                .header("Authorization", "Bearer fake-token") // <--- simula um token
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Convite enviado com sucesso."));
    }

    @WithMockUser(username = "aluno", roles = { "ALUNO" })
    @Test
    void deveResponderConviteComSucesso() throws Exception {
        InvitationResponseDTO dto = new InvitationResponseDTO(1, InvitationStatus.ACCEPTED);

        mockMvc.perform(post("/invitations/respond")
                .header("Authorization", "Bearer fake-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Resposta registrada com sucesso."));
    }
}
