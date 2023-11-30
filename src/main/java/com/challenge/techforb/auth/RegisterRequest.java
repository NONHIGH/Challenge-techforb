package com.challenge.techforb.auth;

import com.challenge.techforb.enums.TypeDocument;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    @NotEmpty
    @Size(min = 1, max = 40)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 40)
    private String lastname;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private TypeDocument type_document;

    @NotEmpty
    @Email(message = "La dirección de correo electrónico no es válida")
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    @Size(min = 1, max = 20)
    private int document_number;
}