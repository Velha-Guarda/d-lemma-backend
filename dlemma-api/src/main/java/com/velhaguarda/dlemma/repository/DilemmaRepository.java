package com.velhaguarda.dlemma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.velhaguarda.dlemma.entity.Dilemma;

public interface DilemmaRepository extends JpaRepository<Dilemma, Integer> {
}
