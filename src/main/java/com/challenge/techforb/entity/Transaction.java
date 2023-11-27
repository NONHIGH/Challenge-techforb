package com.challenge.techforb.entity;

import java.math.BigDecimal;
// import java.sql.Date;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal amount;

    private String transactionType;
    /*
     * incluyo la hora por si las dudas.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

}
