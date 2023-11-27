package com.challenge.techforb.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TransactionDTO {
    private BigDecimal ammount;
    private String transactionType;
    private Date date;
}
