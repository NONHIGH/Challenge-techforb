package com.challenge.techforb.controller;

import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.techforb.auth.jwt.JwtService;
import com.challenge.techforb.dto.UserDTO;
import com.challenge.techforb.service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://unicomer-challenge.vercel.app/")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping()
    public ResponseEntity<?> getDataOfUser(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            if(cookies == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron las credenciales del usuario");
            }
            String jwtCookieValue = Stream.of(cookies)
                    .filter(cookie -> "user".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
            if (jwtCookieValue != null) {
                Claims claims = jwtService.decodeJwt(jwtCookieValue);
                long userId = (long) claims.get("userId", Long.class);
                ResponseEntity<UserDTO> userFound = userService.getUserById(userId);
                if(userFound.getStatusCode() == HttpStatus.OK){
                    return userFound;
                }else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cookie no encontrada");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // @PostMapping("/{id}")
    // public ResponseEntity<Transaction> createTransaction(
    // @Valid
    // @RequestBody(required = true) TransactionDTO newTransaction,
    // @PathVariable(name = "id", required = true) Long idUser
    // ) {
    // return null;
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Transaction> modifyTransaction(
    // @Valid
    // @RequestBody(required = true) TransactionDTO transactionDTO,
    // @PathVariable(name = "id") Long id
    // ){

    // return null;
    // }
}
