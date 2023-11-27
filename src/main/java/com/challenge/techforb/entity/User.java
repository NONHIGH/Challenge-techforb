package com.challenge.techforb.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Entity
@AllArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Size(min = 1, max = 40)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 40)
    private String lastname;
    
    @NotEmpty
    @Email(message = "La dirección de correo electrónico no es valida")
    private String email;
    
    @NotEmpty
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Card> cards = new HashSet<>();


    public Set<Transaction> getTransactions(){
        return this.transactions;
    }

    public void setTransactions(Set<Transaction> transaction){
        this.transactions = transaction;
    }

    public Set<Card> getCards(){
        return this.cards;
    }

    public void setCards(Set<Card> cards){
        this.cards = cards;
    }

    
}
