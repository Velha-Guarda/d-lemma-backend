package com.velhaguarda.dlemma.repository;

import com.velhaguarda.dlemma.entity.PandoraBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PandoraBoxRepository extends JpaRepository<PandoraBox, Integer> {
}