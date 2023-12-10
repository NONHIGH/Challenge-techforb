package com.challenge.techforb.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.challenge.techforb.entity.Card;
import com.challenge.techforb.enums.PaidState;
import com.challenge.techforb.enums.TransactionType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {

    private long id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private Date date;
    private Card senderCard;
    private Card recipientCard;
    private PaidState paidState;

    private String message;
}
