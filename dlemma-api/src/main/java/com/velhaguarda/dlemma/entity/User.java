package com.velhaguarda.dlemma.entity;

import java.util.UUID;

import com.velhaguarda.dlemma.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User { // espelho do banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = true, length = 100)
    private String name;

    @Column(nullable = true, unique = true, length = 150)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true, length = 100)
    private String graduation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Role role;
}
