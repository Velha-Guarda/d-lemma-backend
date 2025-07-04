package com.velhaguarda.dlemma.controller;

import com.velhaguarda.dlemma.dto.ChatMessageDTO;
import com.velhaguarda.dlemma.entity.ChatMessage;
import com.velhaguarda.dlemma.entity.Dilemma;
import com.velhaguarda.dlemma.repository.ChatMessageRepository;
import com.velhaguarda.dlemma.repository.DilemmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatMessageRepository chatMessageRepository;
    private final DilemmaRepository dilemmaRepository;

    @GetMapping("/{dilemmaId}")
    public List<ChatMessageDTO> getMessages(@PathVariable int dilemmaId) {
        Optional<Dilemma> dilemmaOpt = dilemmaRepository.findById(dilemmaId);
        if (dilemmaOpt.isEmpty()) {
            throw new IllegalArgumentException("Dilema não encontrado");
        }

        List<ChatMessage> messages = chatMessageRepository.findByDilemmaOrderByTimestampAsc(dilemmaOpt.get());
        return messages.stream()
                .map(msg -> new ChatMessageDTO(
                        msg.getSender().getId(),
                         msg.getSender().getName(),
                        dilemmaId,
                        msg.getContent()
                ))
                .collect(Collectors.toList());
    }
}
