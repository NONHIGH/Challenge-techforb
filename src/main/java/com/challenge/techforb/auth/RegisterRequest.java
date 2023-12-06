package com.challenge.techforb.auth;

import com.challenge.techforb.enums.TypeDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    private String name;

    private String lastname;

    private TypeDocument type_document;

    private String email;

    private String password;

    private int document_number;
}