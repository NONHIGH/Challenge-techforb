package com.challenge.techforb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.techforb.entity.Card;
import com.challenge.techforb.entity.User;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByUserAndIsPrincipalTrue(User user);

    List<Card> findAllByUserId(long userId);
}
