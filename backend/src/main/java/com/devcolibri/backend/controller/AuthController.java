package com.devcolibri.backend.controller;

import com.devcolibri.backend.dto.LoginRequest;
import com.devcolibri.backend.dto.LoginResponse;
import com.devcolibri.backend.dto.RegisterRequest;
import com.devcolibri.backend.model.User;
import com.devcolibri.backend.repository.UserRepository;
import com.devcolibri.backend.security.JwtService;
import com.devcolibri.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Rejestracja zakończona sukcesem. Sprawdź maila w celu aktywacji konta.");
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activate(@RequestParam String code) {
        boolean activated = userService.activateUser(code);
        if (activated) {
            return ResponseEntity.ok("Konto aktywowane");
        } else {
            return ResponseEntity.badRequest().body("Nieprawidłowy kod aktywacji");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwt = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
