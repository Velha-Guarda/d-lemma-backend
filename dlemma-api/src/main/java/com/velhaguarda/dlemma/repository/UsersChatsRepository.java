package com.velhaguarda.dlemma.repository;

import com.velhaguarda.dlemma.entity.UsersChats;
import com.velhaguarda.dlemma.entity.UsersChatsId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersChatsRepository extends JpaRepository<UsersChats, UsersChatsId> {
     //listar todos os UsersChats de um determinado dilema
    List<UsersChats> findByDilemma_IdDilemma(int dilemmaId);
}
