package com.challenge.techforb.auth;

import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.techforb.auth.jwt.JwtService;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.enums.Role;
import com.challenge.techforb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<User> userFound = userRepository.findByTypeDocumentAndNumberDocument(loginRequest.getType_document(), loginRequest.getDocument_number());
        if (userFound.isPresent()) {
            User user = userFound.get();
            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));
                String token = jwtService.getToken((UserDetails) authentication.getPrincipal());
                return ResponseEntity.ok(AuthResponse.builder()
                    .token(token).build());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    public ResponseEntity<Object> register(RegisterRequest registerRequest) {
        Optional<User> userFoundOptional = userRepository.findByTypeDocumentAndNumberDocument(registerRequest.getType_document(), registerRequest.getDocument_number());
        if(userFoundOptional.isPresent()){
            Object error = new Exception("El número de documento ya está siendo utilizado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        User user = User
            .builder()
            .email(registerRequest.getEmail())
            .numberDocument(registerRequest.getDocument_number())
            .lastname(registerRequest.getLastname())
            .name(registerRequest.getName())
            .role(Role.ADMIN)
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .typeDocument(registerRequest.getType_document())
            .build();
        userRepository.save(user);

        return ResponseEntity.ok(AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build());
    }

}
