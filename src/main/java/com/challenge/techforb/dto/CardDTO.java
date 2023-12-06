package com.challenge.techforb.dto;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDTO {

    private long id;

    @NotEmpty(message = "La titularidad de la tarjeta no puede estar vacía")
    private String headline;

    private long numberCard;

    @NotNull(message = "La fecha de vencimiento no puede ser nula")
    private Date dueDate;

    private boolean isPrincipal;

    @NotNull(message = "El código de seguridad no puede ser nulo")
    private String securityCode;

    @NotNull(message = "El saldo no puede ser nulo")
    private BigDecimal balance;

    private String message;

}
