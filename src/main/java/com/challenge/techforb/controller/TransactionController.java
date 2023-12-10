package com.challenge.techforb.controller;

import java.util.stream.Stream;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.techforb.auth.jwt.JwtService;
import com.challenge.techforb.dto.ResponseDTO;
import com.challenge.techforb.dto.TransactionDTO;
import com.challenge.techforb.dto.TransactionPostDTO;
import com.challenge.techforb.service.TransactionService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://unicomer-challenge.vercel.app/")
public class TransactionController {

    private final TransactionService transactionService;
    private final JwtService jwtService;

    @GetMapping()
    public ResponseEntity<?> getAllTransactionsWithPages(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        try {
            Cookie jwtCookie = getJwtCookie(request);
            if (jwtCookie != null) {
                Claims claims = jwtService.decodeJwt(jwtCookie.getValue());
                long userId = (long) claims.get("userId", Long.class);
                PageRequest pageable = PageRequest.of(page, limit);
                return transactionService.getUserTransactionsResponse(userId, pageable);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseDTO.builder().message("No se encontro la cookie, vuelva a loguearse").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.builder().message("Error en el servidor: " + e.getMessage()).build());
        }

    }

    @PostMapping()
    public ResponseEntity<?> createTransaction(
            HttpServletRequest request,
            @RequestBody TransactionPostDTO transaction) {
        try {
            Cookie jwtCookie = getJwtCookie(request);
            if (jwtCookie != null) {
                Claims claims = jwtService.decodeJwt(jwtCookie.getValue());
                long userId = (long) claims.get("userId", Long.class);
                return transactionService.carryOutTheTransaction(transaction, userId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseDTO.builder().message("No se pudieron encontrar las credenciales.").build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.builder().message("Error en el servidor: " + e.getMessage()).build());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(
            @PathVariable(name = "id") long transactionId) {
        return transactionService.getTransactionByIdResponse(transactionId);
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
