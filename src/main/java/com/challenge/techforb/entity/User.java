package com.challenge.techforb.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.challenge.techforb.enums.Role;
import com.challenge.techforb.enums.TypeDocument;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Size(min = 1, max = 40)
    private String name;


    @NotEmpty
    @Size(min = 1, max = 40)
    private String lastname;

    Role role;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private TypeDocument typeDocument;

    @NonNull
    @Column(nullable = false)
    private int numberDocument;

    @NotEmpty
    @Email(message = "La dirección de correo electrónico no es valida")
    private String email;
    
    @NotEmpty
    private String password;

    // @Builder.Default
    // @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    // private Set<Transaction> transactions = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Transaction> sentTransactions = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private Set<Transaction> receivedTransactions = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Card> cards = new HashSet<>();


    // public Set<Transaction> getTransactions(){
    //     return this.transactions;
    // }

    // public void setTransactions(Set<Transaction> transaction){
    //     this.transactions = transaction;
    // }

    public Set<Card> getCards(){
        return this.cards;
    }

    public void setCards(Set<Card> cards){
        this.cards = cards;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.numberDocument+":"+this.typeDocument;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public TypeDocument getTypeDocument(){
        return this.typeDocument;
    }
}
