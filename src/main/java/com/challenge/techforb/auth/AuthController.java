package com.challenge.techforb.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.techforb.dto.ResponseDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> logout(
        HttpServletRequest request,
        HttpServletResponse response
        ) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                System.out.println("nombre de las cookies ===> "+ cookie.getName());
                if("user".equals(cookie.getName()) || "session".equals(cookie.getName())){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    System.out.println("cookie con el nombrede : ===> "+ cookie.getName() + " fue eliminada");
                }
            }
            System.out.println("entre a las cookies");
            return ResponseEntity.ok(ResponseDTO.builder().message("Cookie eliminada correctamente").build());
        
    }
}
