package com.challenge.techforb.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    
    private final AuthService authService;
    
    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }


    @PostMapping(value = "register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }
}
