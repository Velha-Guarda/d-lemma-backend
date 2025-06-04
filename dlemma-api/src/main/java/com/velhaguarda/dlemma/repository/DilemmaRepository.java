package com.velhaguarda.dlemma.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.velhaguarda.dlemma.entity.Dilemma;
import com.velhaguarda.dlemma.entity.UsersChats;

public interface DilemmaRepository extends JpaRepository<Dilemma, Integer> {

    @Query("SELECT uc FROM UsersChats uc WHERE uc.user.id = :userId")
    List<UsersChats> findParticipationsByUserId(@Param("userId") UUID userId);
}
