package com.velhaguarda.dlemma.entity;

import com.velhaguarda.dlemma.enums.InvitationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "users_chats")
@IdClass(UsersChatsId.class)
public class UsersChats {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Dilemma dilemma;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt = LocalDateTime.now();

    private int score = 0;

    @Enumerated(EnumType.STRING)
    private InvitationStatus invitation;
}
