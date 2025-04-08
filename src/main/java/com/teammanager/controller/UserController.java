package com.teammanager.controller;

import com.teammanager.model.User;
import com.teammanager.repository.UserRepository;
import com.teammanager.security.CurrentUser;
import com.teammanager.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/{username}")
    public User getUserProfile(@PathVariable(value = "username") String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
} 