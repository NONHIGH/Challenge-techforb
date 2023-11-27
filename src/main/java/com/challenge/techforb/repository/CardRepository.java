package com.challenge.techforb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.techforb.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    
}
