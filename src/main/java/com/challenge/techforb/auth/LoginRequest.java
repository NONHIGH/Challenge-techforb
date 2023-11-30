package com.challenge.techforb.auth;

import com.challenge.techforb.enums.TypeDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRequest {
    TypeDocument type_document;
    int document_number;
    String password;
}