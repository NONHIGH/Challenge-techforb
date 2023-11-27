package com.challenge.techforb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {



     @PostMapping("/{id}")
    public ResponseEntity<?> login(
        @PathVariable() Long id
        ) {
        //funcion de la interfaz repository para este servicio
        //luego sigo con los test unitarios

        return ResponseEntity.ok().body(id);
    }

    // @PostMapping("/{id}")
    // public ResponseEntity<?> register(
    //     @Valid
    //     @RequestBody(required = true)  newTransaction
    //     ) {
    //     return null;
    // }
}
