package com.challenge.techforb.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.challenge.techforb.dto.CardDTO;
import com.challenge.techforb.entity.Card;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.repository.CardRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImplementation implements CardService {

    private final UserService userService;
    private final CardRepository cardRepository;

    @Override
    public ResponseEntity<?> saveCardResponse(CardDTO newCard, long userId) {
        try {
            User user = getUserFromService(userId);

            Card cardToSave = buildCardPrincipalOrElse(newCard, user);
            cardRepository.save(cardToSave);
            return ResponseEntity.ok().body(CardDTO.builder()
                    .message("Tarjeta guardada")
                    .build());

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en el servidor, contacte a su provedor de servicios");
        }

    }

    @Override
    public ResponseEntity<CardDTO> editCardResponse(CardDTO cardToEdit, long userId) {
        try {
            getUserFromService(userId);
            Card cardFound = getCardById(cardToEdit.getId());
            cardFound.setNumberCard(cardToEdit.getNumberCard());
            cardFound.setBalance(cardToEdit.getBalance());
            cardFound.setDueDate(cardToEdit.getDueDate());
            cardFound.setHeadline(cardToEdit.getHeadline());
            cardFound.setSecurityCode(cardToEdit.getSecurityCode());
            cardRepository.save(cardFound);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CardDTO.builder()
                    .message("Tarjeta actualizada")
                    .build());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CardDTO.builder()
                    .message("Error al intentar actualizar la tarjeta: " + e.getMessage())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CardDTO.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @Override
    public ResponseEntity<CardDTO> getCardResponseById(long id) {
        try {
            Card cardFound = getCardById(id);
            CardDTO card = CardDTO.builder()
                    .id(cardFound.getId())
                    .balance(cardFound.getBalance())
                    .headline(cardFound.getHeadline())
                    .numberCard(cardFound.getNumberCard())
                    .securityCode(cardFound.getSecurityCode())
                    .dueDate(cardFound.getDueDate())
                    .build();
            return ResponseEntity.ok().body(card);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CardDTO.builder()
                    .message(e.getMessage())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CardDTO.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @Override
    public ResponseEntity<CardDTO> deleteCardResponseById(long id) {
        try {
            deleteCardById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CardDTO.builder()
                    .message("Se elimino la tarjeta exitosamente")
                    .build());

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CardDTO.builder()
                    .message(e.getMessage())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CardDTO.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @Override
    public Card getCardById(long id) {
        try {
            Optional<Card> cardFound = cardRepository.findById(id);
            if (!cardFound.isPresent()) {
                throw new EntityNotFoundException("Esta tarjeta no existe");
            }
            return cardFound.get();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Error en la busqueda de la tarjeta: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error en el servidor: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteCardById(long id) {
        try {
            Card cardFound = getCardById(id);
            cardRepository.delete(cardFound);
            return true;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Error al eliminar la tarjeta: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error por parte del servidor: " + e.getMessage());
        }
    }

    public User getUserFromService(long userId) {
        try {
            User userFound = userService.getUserById(userId);
            if (userFound == null) {
                throw new EntityNotFoundException("Usuario no encontrado");
            }
            return userFound;
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Error en la busqueda del usuario: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error en el servidor: " + e.getMessage());
        }
    }

    @Override
    public Card getCardPrincipalByUserId(long userId) {
        try {
            User user = getUserFromService(userId);
            Optional<Card> principalCard = cardRepository.findByUserAndIsPrincipalTrue(user);
            if (!principalCard.isPresent()) {
                throw new EntityNotFoundException(user.getEmail() + " " + user.getName()
                        + "no tiene una tarjeta principal a la que depositar dinero");
            }
            return principalCard.get();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Error al buscar la tarjeta principal: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error en el servidor: " + e.getMessage());
        }
    }

    @Override
    public Card obtainCardWithOutSensitiveData(Card card) {
        return Card.builder()
                .numberCard(card.getNumberCard())
                .headline(card.getHeadline())
                .isPrincipal(card.isPrincipal())
                .build();
    }

    private Card buildCardPrincipalOrElse(CardDTO cardToBuild, User user) {
        boolean isFirst = user.getCards().isEmpty();
        System.out.println(isFirst + " " + user.getCards());
        return Card.builder()
                .balance(cardToBuild.getBalance())
                .dueDate(cardToBuild.getDueDate())
                .headline(cardToBuild.getHeadline())
                .securityCode(cardToBuild.getSecurityCode())
                .user(user)
                .isPrincipal(isFirst)
                .numberCard(cardToBuild.getNumberCard())
                .build();
    }
}
