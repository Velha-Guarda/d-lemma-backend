package com.velhaguarda.dlemma.websocket;

import com.velhaguarda.dlemma.dto.ChatMessageDTO;
import com.velhaguarda.dlemma.entity.ChatMessage;
import com.velhaguarda.dlemma.entity.Dilemma;
import com.velhaguarda.dlemma.entity.User;
import com.velhaguarda.dlemma.repository.ChatMessageRepository;
import com.velhaguarda.dlemma.repository.DilemmaRepository;
import com.velhaguarda.dlemma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatMessageRepository chatMessageRepository;
    private final DilemmaRepository dilemmaRepository;
    private final UserRepository userRepository;

    @MessageMapping("/chat.sendMessage") // recebe do front via /app/chat.sendMessage
    @SendTo("/topic/dilemma") // envia para todos inscritos em /topic/dilemma
    public ChatMessageDTO sendMessage(ChatMessageDTO messageDTO) {
        Optional<Dilemma> dilemmaOpt = dilemmaRepository.findById(messageDTO.getDilemmaId());
        Optional<User> senderOpt = userRepository.findById(messageDTO.getSenderId());

        if (dilemmaOpt.isEmpty() || senderOpt.isEmpty()) {
            throw new IllegalArgumentException("Dilema ou Usuário não encontrado");
        }

        ChatMessage message = new ChatMessage();
        message.setDilemma(dilemmaOpt.get());
        message.setSender(senderOpt.get());
        message.setContent(messageDTO.getContent());

        chatMessageRepository.save(message);

        return messageDTO; // enviado de volta para o front (pode customizar com nome, horário, etc.)
    }
}