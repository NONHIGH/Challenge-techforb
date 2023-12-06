package com.challenge.techforb.dto;

import java.math.BigDecimal;

import com.challenge.techforb.enums.TransactionType;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionPostDTO {
    @NotNull(message = "El id no puede ser nulo")
    private long idCardSender;

    private long idUserReceveidAmount;
    @NotNull(message = "El monto no puede ser nulo")
    private BigDecimal amount;
    @NotNull(message = "El tipo de transacción no puede ser nulo")
    private TransactionType transactionType;
}
