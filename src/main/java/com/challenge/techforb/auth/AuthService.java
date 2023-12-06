package com.challenge.techforb.auth;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.techforb.auth.jwt.JwtService;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.enums.Role;
import com.challenge.techforb.enums.TypeDocument;

import com.challenge.techforb.repository.UserRepository;

// import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> login(LoginRequest loginRequest, HttpServletResponse response) {
        try {
            String typeDocument = loginRequest.getType_document().name();

            Page<User> userFound;

            switch (typeDocument) {
                case "DNI":
                    userFound = userRepository.findByNumberDocumentAndTypeDocument(loginRequest.getDocument_number(),
                            TypeDocument.DNI, PageRequest.of(0, 1));
                    break;
                case "PASAPORTE":
                    userFound = userRepository.findByNumberDocumentAndTypeDocument(loginRequest.getDocument_number(),
                            TypeDocument.PASAPORTE, PageRequest.of(0, 1));
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de documento no reconocido");
            }
            if (!userFound.isEmpty()) {
                User user = userFound.getContent().get(0);
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));

                String token = jwtService.getToken((UserDetails) authentication.getPrincipal());
                addTokenToCookie(response, token);
                return ResponseEntity.ok(AuthResponse.builder().token(token).build());
            }
        } catch (BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de documento no reconocido");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la autenticación");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<Object> register(RegisterRequest registerRequest, HttpServletResponse response) {
        try {
            String typeDocument = registerRequest.getType_document().name();
            Optional<User> userFoundOptional;

            switch (typeDocument) {
                case "DNI":
                    userFoundOptional = userRepository.findByTypeDocumentAndNumberDocument(TypeDocument.DNI,
                            registerRequest.getDocument_number());
                    break;
                case "PASAPORTE":
                    userFoundOptional = userRepository.findByTypeDocumentAndNumberDocument(TypeDocument.PASAPORTE,
                            registerRequest.getDocument_number());
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de documento no reconocido");
            }

            if (!userFoundOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El número de documento ya está siendo utilizado");
            }

            User user = User.builder()
                    .email(registerRequest.getEmail())
                    .numberDocument(registerRequest.getDocument_number())
                    .lastname(registerRequest.getLastname())
                    .name(registerRequest.getName())
                    .role(Role.USER)
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .typeDocument(registerRequest.getType_document())
                    .build();

            userRepository.save(user);

            AuthResponse token = AuthResponse.builder().token(jwtService.getToken(user)).build();
            addTokenToCookie(response, token.getToken());

            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de documento no reconocido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el registro");
        }
    }

    
    private void addTokenToCookie(HttpServletResponse response, String token) {
        response.setHeader("Set-Cookie", "user=" + token + "; HttpOnly; Secure; SameSite=None; Max-Age=" +  24 * 60 * 60 + "; Path=/");
    }
    
}
