package com.challenge.techforb.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.challenge.techforb.dto.TransactionDTO;
import com.challenge.techforb.dto.TransactionPostDTO;

public interface TransactionService {
    
    public ResponseEntity<Page<TransactionDTO>> getUserTransactionsResponse(long idUser, Pageable pages);

    public ResponseEntity<TransactionDTO> getTransactionByIdResponse(long transactionId);

    public ResponseEntity<TransactionDTO> carryOutTheTransaction(TransactionPostDTO transactionData);

}
