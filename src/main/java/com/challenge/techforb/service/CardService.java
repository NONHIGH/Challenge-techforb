package com.challenge.techforb.service;

import org.springframework.http.ResponseEntity;

import com.challenge.techforb.dto.CardDTO;
import com.challenge.techforb.entity.Card;

public interface CardService {

    public ResponseEntity<Card> saveCard(CardDTO newCard ,Long userId);
    public ResponseEntity<Card> editCard(CardDTO newCard ,Long userId);
    public ResponseEntity<Card> getCard(Long id);
    public ResponseEntity<Card> deleteCard(Long id);


}
