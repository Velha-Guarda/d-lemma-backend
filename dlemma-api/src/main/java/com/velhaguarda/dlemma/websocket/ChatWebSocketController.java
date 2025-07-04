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
import org.springframework.stereotype.Controller;
import java.util.Optional;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RequiredArgsConstructor
@Controller
public class ChatWebSocketController {

    private final ChatMessageRepository chatMessageRepository;
    private final DilemmaRepository dilemmaRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDTO messageDTO) {
        Optional<Dilemma> dilemmaOpt = dilemmaRepository.findById(messageDTO.getDilemmaId());
        Optional<User> senderOpt = userRepository.findById(messageDTO.getSenderId());

        if (dilemmaOpt.isEmpty() || senderOpt.isEmpty()) {
            throw new IllegalArgumentException("Dilema ou Usuário não encontrado");
        }

        Dilemma dilemma = dilemmaOpt.get();
        User sender = senderOpt.get();

        // Preenche o nome do remetente antes de enviar
        messageDTO.setSenderName(sender.getName()); // ou getNome(), conforme sua entidade

        // Salvar no banco
        ChatMessage message = new ChatMessage();
        message.setDilemma(dilemma);
        message.setSender(sender);
        message.setContent(messageDTO.getContent());
        chatMessageRepository.save(message);

        // Envia para o tópico do dilema
        messagingTemplate.convertAndSend(
            "/topic/dilemma." + messageDTO.getDilemmaId(),
            messageDTO
        );
    }
}
