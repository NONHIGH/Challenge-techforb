package com.challenge.techforb.controller;

import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.techforb.auth.jwt.JwtService;
import com.challenge.techforb.dto.CardDTO;
import com.challenge.techforb.service.CardService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/card")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://unicomer-challenge.vercel.app/")
public class CardController {

    private final CardService cardService;
    private final JwtService jwtService;

    @GetMapping(value = "/{id}")

    public ResponseEntity<CardDTO> getCardById(@PathVariable(name = "id") long id) {
        return cardService.getCardResponseById(id);
    }

    @GetMapping()
    public ResponseEntity<?> getAllCardsOfUser(HttpServletRequest request) {
        try {
            Cookie jwtCookie = getJwtCookie(request);
            if (jwtCookie != null) {
                Claims claims = jwtService.decodeJwt(jwtCookie.getValue());
                long userId = (long) claims.get("userId", Long.class);
                return cardService.getAllCardsOfUser(userId);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CardDTO.builder().message("No se encontraron las credenciales del usuario").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CardDTO.builder()
                    .message("Error al desencriptar la cookie")
                    .build());
        }
    }

    @PostMapping(value = "/save/{userId}")
    public ResponseEntity<?> saveCard(
            @Valid @RequestBody CardDTO newCard,
            @PathVariable(value = "userId") long userId) {
        return cardService.saveCardResponse(newCard, userId);
    }

    @PatchMapping(value = "/edit/{cardId}")
    public ResponseEntity<CardDTO> editCard(
            @Valid @RequestBody CardDTO newCard,
            @PathVariable(name = "cardId") long cardId) {
        return cardService.editCardResponse(newCard, cardId);
    }

    @DeleteMapping(value = "/delete/{cardId}")
    public ResponseEntity<CardDTO> deleteCard(
            @PathVariable(name = "cardId") long cardId) {
        return cardService.deleteCardResponseById(cardId);
    }

    public Cookie getJwtCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Stream.of(cookies)
                .filter(cookie -> "user".equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }
}
