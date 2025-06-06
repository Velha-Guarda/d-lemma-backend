package com.velhaguarda.dlemma.repository;

import com.velhaguarda.dlemma.entity.UsersChats;
import com.velhaguarda.dlemma.entity.UsersChatsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersChatsRepository extends JpaRepository<UsersChats, UsersChatsId> {
}
