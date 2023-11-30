package com.challenge.techforb.dto;

import com.challenge.techforb.enums.TypeDocument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private long id;
    private String name;
    private String lastName;
    private TypeDocument typeDocument;
    private int numberDocument;
    private String email;
}
