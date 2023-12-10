package com.challenge.techforb.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.techforb.dto.ResponseDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authService.login(loginRequest, response);
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        return authService.register(registerRequest, response);
    }

    @DeleteMapping(value = "logout")
    public ResponseEntity<?> logout(HttpServletResponse response,
            @CookieValue(name = "user", required = false) Cookie cookie,
            @CookieValue(name = "session", required = false) Cookie cookieS
            
            ) {
        if (cookie != null) {
            cookie.setValue("");
            cookieS.setValue("");
            cookie.setMaxAge(0);
            cookieS.setMaxAge(0);
            cookie.setPath("/");
            cookieS.setPath("/");
            response.addCookie(cookie);
            response.addCookie(cookieS);
            System.out.println("entre aqui, son la cookies" + cookie + cookieS);
            return ResponseEntity.ok(ResponseDTO.builder().message("Cookie eliminada correctamente").build());
        } else {
            return ResponseEntity.ok(ResponseDTO.builder().message("No se encontró la cookie para eliminar").build());
        }
    }
}
