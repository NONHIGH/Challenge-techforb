package com.challenge.techforb.auth;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.challenge.techforb.enums.TypeDocument;
import com.challenge.techforb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String numberAndTypeDocument) throws UsernameNotFoundException {
        String[] wordsSeparated = numberAndTypeDocument.split(":");
            if(wordsSeparated.length != 2){
                throw new UsernameNotFoundException("Formato invalido");
            }
        try {
            int documentNumber = Integer.parseInt(wordsSeparated[0]);
            TypeDocument typeDocument = TypeDocument.valueOf(wordsSeparated[1]);
            return userRepository.findByTypeDocumentAndNumberDocument(typeDocument,documentNumber).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        } catch (NumberFormatException | IllegalAccessError e) {
            throw new UsernameNotFoundException("Documento invalido");
        }
    }
    
}
