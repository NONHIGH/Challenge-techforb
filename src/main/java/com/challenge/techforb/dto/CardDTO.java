package com.challenge.techforb.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class CardDTO {
    private String headline;

    private long numberCard;

    private Date dueDate;

    private int securityCode;

    private BigDecimal balance;
}
