package com.velhaguarda.dlemma.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private UUID senderId;
    private String senderName;
    private int dilemmaId;
    private String content;
}