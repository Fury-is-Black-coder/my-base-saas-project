package com.devcolibri.backend.service;

import com.devcolibri.backend.dto.RegisterRequest;
import com.devcolibri.backend.model.User;
import com.devcolibri.backend.enums.UserRole;
import com.devcolibri.backend.enums.UserStatus;
import com.devcolibri.backend.repository.UserRepository;
import com.devcolibri.backend.service.MailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;

    public UserService(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.mailService = mailService;
    }

    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setLanguage(request.getLanguage());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.INACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setNewsletter(request.isNewsletter());
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        // TODO: wysłać mail aktywacyjny
        mailService.sendActivationEmail(user.getEmail(), user.getActivationCode());
    }

    public boolean activateUser(String code) {
        Optional<User> userOptional = userRepository.findByActivationCode(code);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        user.setStatus(UserStatus.ACTIVE);
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Użytkownik nie znaleziony: " + email));
    }
}
