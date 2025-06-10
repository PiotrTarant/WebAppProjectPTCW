package com.teammanager.controller;

import com.teammanager.dto.ApiResponse;
import com.teammanager.dto.JwtResponse;
import com.teammanager.dto.LoginRequest;
import com.teammanager.dto.RegisterRequest;
import com.teammanager.model.RoleName;
import com.teammanager.model.User;
import com.teammanager.security.JwtTokenProvider;
import com.teammanager.security.UserPrincipal;
import com.teammanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String role = userPrincipal.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElse("ROLE_USER");

        return ResponseEntity.ok(new JwtResponse(jwt, userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getEmail(), role));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {
        log.info("Received registration request for user: {}", registerRequest.getUsername());
        log.debug("Full registration request: {}", registerRequest);

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
            log.error("Validation errors: {}", errorMessage);
            return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));
        }

        try {
            User user = userService.createUser(registerRequest, RoleName.ROLE_USER);
            log.info("Successfully created user: {}", user.getUsername());

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    registerRequest.getUsername(),
                    registerRequest.getPassword()
                )
            );
            log.debug("User authenticated successfully");

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            log.debug("JWT token generated successfully");

            String role = user.getRoles().stream()
                    .findFirst()
                    .map(userRole -> userRole.getName().name())
                    .orElse("ROLE_USER");

            return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail(), role));
        } catch (Exception e) {
            log.error("Error during user registration: ", e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error during registration: " + e.getMessage()));
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth controller is working!");
    }
} 