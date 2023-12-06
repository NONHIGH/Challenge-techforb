package com.challenge.techforb.service;


import org.springframework.http.ResponseEntity;

import com.challenge.techforb.dto.CardDTO;
import com.challenge.techforb.entity.Card;

public interface CardService {

    public ResponseEntity<?> saveCardResponse(CardDTO newCard ,long userId);
    public ResponseEntity<CardDTO> editCardResponse(CardDTO newCard ,long cardId);
    public ResponseEntity<CardDTO> getCardResponseById(long id);
    public ResponseEntity<CardDTO> deleteCardResponseById(long id);
    public Card getCardPrincipalByUserId(long userId);
    public Card getCardById(long id) ;
    public boolean deleteCardById(long id);
    public Card obtainCardWithOutSensitiveData(Card card);
    public ResponseEntity<?> getAllCardsOfUser(long id);
}
