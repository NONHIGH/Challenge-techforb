package com.challenge.techforb.auth;

import com.challenge.techforb.enums.TypeDocument;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRequest {
    @Enumerated(EnumType.STRING)
    TypeDocument type_document;
    int document_number;
    String password;
}