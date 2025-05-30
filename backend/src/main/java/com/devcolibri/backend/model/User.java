package com.devcolibri.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import com.devcolibri.backend.enums.UserRole;
import com.devcolibri.backend.enums.UserStatus;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;
    private String language;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.INACTIVE;

    private String googleId;
    private String facebookId;
    private String githubId;

    private boolean newsletter;

    @Column(name = "activation_code")
    private String activationCode;

    // Gettery/settery/konstruktory (lub @Data z Lombok)
}

