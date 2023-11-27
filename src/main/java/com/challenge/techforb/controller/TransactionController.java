package com.challenge.techforb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.techforb.dto.TransactionDTO;
import com.challenge.techforb.entity.Transaction;
import com.challenge.techforb.entity.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    // @GetMapping()
    // public ResponseEntity<Array<TransactionDTO>> getTransactions(){
        
    //     return ResponseEntity.ok().body();
    // }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllTransactionsWithPages(
        @PathVariable(name = "id", required = true) Long id
        ) {
        //funcion de la interfaz repository para este servicio
        //luego sigo con los test unitarios

        return ResponseEntity.ok().body(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Transaction> createTransaction(
        @Valid
        @RequestBody(required = true) TransactionDTO newTransaction, 
        @PathVariable(name = "id", required = true) Long idUser
        ) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> modifyTransaction(
        @Valid
        @RequestBody(required = true) TransactionDTO transactionDTO,
        @PathVariable(name = "id") Long id
    ){
        
        return null;
    }
}
