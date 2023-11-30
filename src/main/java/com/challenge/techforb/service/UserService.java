package com.challenge.techforb.service;


import org.springframework.http.ResponseEntity;

import com.challenge.techforb.dto.UserDTO;

public interface UserService {
    public ResponseEntity<UserDTO> getUserById(long id);

}
