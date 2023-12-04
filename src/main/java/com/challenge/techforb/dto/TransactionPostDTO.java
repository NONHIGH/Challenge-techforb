package com.challenge.techforb.dto;

import java.math.BigDecimal;

import com.challenge.techforb.enums.TransactionType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionPostDTO {
    private long idCardSender;
    private long idUserReceveidAmount;
    private BigDecimal amount;
    private TransactionType transactionType;
}
