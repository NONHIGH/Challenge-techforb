package com.challenge.techforb.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDTO {

    private long id;
    private String headline;

    private long numberCard;

    private Date dueDate;

    private boolean isPrincipal;

    private String securityCode;

    private BigDecimal balance;

    private String message;
}
