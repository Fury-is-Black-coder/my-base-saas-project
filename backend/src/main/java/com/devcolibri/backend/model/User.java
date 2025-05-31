package com.devcolibri.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.devcolibri.backend.enums.UserRole;
import com.devcolibri.backend.enums.UserStatus;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;
    private String language;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.INACTIVE;

    private String googleId;
    private String facebookId;
    private String githubId;

    private boolean newsletter;

    @Column(name = "activation_code")
    private String activationCode;

    // Gettery/settery/konstruktory (lub @Data z Lombok)

    // Metody z interfejsu UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status != UserStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ACTIVE;
    }

}

