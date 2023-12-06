package com.challenge.techforb.controller;

import java.math.BigDecimal;

// import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.techforb.dto.TransactionDTO;
import com.challenge.techforb.dto.TransactionPostDTO;
import com.challenge.techforb.enums.TransactionType;
import com.challenge.techforb.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://unicomer-challenge.vercel.app/")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<?> getAllTransactionsWithPages(
            @PathVariable(name = "userId", required = true) Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PageRequest pageable = PageRequest.of(page, limit);
        return transactionService.getUserTransactionsResponse(userId, pageable);
    }

    @PostMapping(value = "/{senderCardId}/{userReceiveId}/{amount}/{transactionType}")
    public ResponseEntity<TransactionDTO> createTransaction(
            @PathVariable(name = "senderCardId") long senderCardId,
            @PathVariable(name = "userReceivedId") Long userReceved,
            @PathVariable(name = "amount") BigDecimal amount,
            @PathVariable(name = "transactionType") TransactionType transactionType) {
        return transactionService
                .carryOutTheTransaction(TransactionPostDTO.builder().amount(amount).idCardSender(senderCardId)
                        .idUserReceveidAmount(senderCardId).transactionType(transactionType).build());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(
            @PathVariable(name = "id") long transactionId) {
        return transactionService.getTransactionByIdResponse(transactionId);
    }
}
