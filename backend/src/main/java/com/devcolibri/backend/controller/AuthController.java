package com.devcolibri.backend.controller;

import com.devcolibri.backend.dto.RegisterRequest;
import com.devcolibri.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

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
}
