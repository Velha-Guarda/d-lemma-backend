package com.velhaguarda.dlemma.repository;

import com.velhaguarda.dlemma.entity.ChatMessage;
import com.velhaguarda.dlemma.entity.Dilemma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findByDilemmaOrderByTimestampAsc(Dilemma dilemma);
}