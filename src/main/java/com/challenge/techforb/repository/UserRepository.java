package com.challenge.techforb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.techforb.entity.User;
import com.challenge.techforb.enums.TypeDocument;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTypeDocumentAndNumberDocument(TypeDocument type_document, int number_document);

    Optional<User> findByName(String username);
}
