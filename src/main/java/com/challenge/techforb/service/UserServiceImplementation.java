package com.challenge.techforb.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.challenge.techforb.dto.UserDTO;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.repository.UserRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Builder
public class UserServiceImplementation implements UserService {
    
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<UserDTO> getUserById(long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if(userOptional.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            User userFound = userOptional.get();
            UserDTO user = UserDTO.builder()
                .id(userFound.getId())
                .name(userFound.getName())
                .lastName(userFound.getLastname())
                .email(userFound.getEmail())
                .typeDocument(userFound.getTypeDocument())
                .numberDocument(userFound.getNumberDocument())
                .build();
                
            return ResponseEntity.ok().body(user);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
}
