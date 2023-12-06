package com.challenge.techforb.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.challenge.techforb.dto.ResponseDTO;
import com.challenge.techforb.dto.TransactionDTO;
import com.challenge.techforb.dto.TransactionPostDTO;
import com.challenge.techforb.entity.Card;
import com.challenge.techforb.entity.Transaction;
import com.challenge.techforb.exceptions.transactions.InsufficientFundsException;
import com.challenge.techforb.repository.TransactionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final CardService cardService;

    private final String errorTransaction = "Error al realizar la transacción: ";

    @Override
    @Transactional
    public ResponseEntity<TransactionDTO> carryOutTheTransaction(TransactionPostDTO transactionData) {
        try {
            Card recipiendCardFound = cardService.getCardById(transactionData.getIdCardSender());
            Card senderCardFound = cardService.getCardPrincipalByUserId(transactionData.getIdUserReceveidAmount());

            if (recipiendCardFound.getBalance().compareTo(transactionData.getAmount()) < 0) {
                throw new InsufficientFundsException(
                        "El usuario no tiene los fondos suficientes para realizar la operación");
            }
            Transaction transaction = Transaction.builder()
                    .amount(transactionData.getAmount())
                    .date(new Date())
                    .transactionType(transactionData.getTransactionType())
                    .recipientCard(recipiendCardFound)
                    .senderCard(senderCardFound)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(TransactionDTO.builder()
                    .amount(transactionData.getAmount())
                    .transactionType(transactionData.getTransactionType())
                    .date(transaction.getDate())
                    .senderCard(cardService.obtainCardWithOutSensitiveData(senderCardFound))
                    .recipientCard(cardService.obtainCardWithOutSensitiveData(recipiendCardFound))
                    .build());
                    
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(TransactionDTO.builder()
                    .message(errorTransaction + e.getMessage())
                    .build());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(TransactionDTO.builder()
                    .message(errorTransaction + e.getMessage())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TransactionDTO.builder()
                    .message(errorTransaction + e.getMessage())
                    .build());
        }
    }

    private TransactionDTO mapToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .transactionType(transaction.getTransactionType())
                .senderCard(transaction.getSenderCard())
                .recipientCard(transaction.getRecipientCard())
                .build();
    }

    @Override
    public ResponseEntity<?> getUserTransactionsResponse(long idUser, Pageable pages) {
        try {
            Page<Transaction> userTransactions = transactionRepository.findAllBySenderCard_User_IdOrRecipientCard_User_Id(idUser, idUser, pages);
            Page<TransactionDTO> transactionsDTO = userTransactions.map(this::mapToDTO);
            return ResponseEntity.ok().body(transactionsDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().message("Error en el servidor: " + e.getMessage()).build());
        }
    }

    @Override
    public ResponseEntity<TransactionDTO> getTransactionByIdResponse(long transactionId) {
        try {
            Transaction transactionFound = getTransactionById(transactionId);
            TransactionDTO transaction = TransactionDTO.builder()
                    .amount(transactionFound.getAmount())
                    .date(transactionFound.getDate())
                    .id(transactionFound.getId())
                    .senderCard(transactionFound.getSenderCard())
                    .recipientCard(transactionFound.getRecipientCard())
                    .transactionType(transactionFound.getTransactionType())
                    .build();
            return ResponseEntity.ok().body(transaction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TransactionDTO.builder()
                    .message(e.getMessage())
                    .build());
        }

    }

    private Transaction getTransactionById(long idTransaction) {
        try {
            Optional<Transaction> transactionFound = transactionRepository.findById(idTransaction);
            if (!transactionFound.isPresent()) {
                throw new EntityNotFoundException("Transacción no encontrada");
            }
            return transactionFound.get();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Error al obtener la transacción: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error en el servidor: " + e.getMessage());
        }
    }

}
