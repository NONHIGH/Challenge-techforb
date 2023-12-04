package com.challenge.techforb.service;


import org.springframework.http.ResponseEntity;

import com.challenge.techforb.dto.UserDTO;
import com.challenge.techforb.entity.User;

public interface UserService {
    public ResponseEntity<UserDTO> getUserDTOById(long id);
    public User getUserById(long id) throws Exception;
}
