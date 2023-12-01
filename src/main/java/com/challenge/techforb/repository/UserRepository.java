package com.challenge.techforb.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.techforb.entity.User;
import com.challenge.techforb.enums.TypeDocument;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByTypeDocumentAndNumberDocument(TypeDocument type_document, int number_document);

    // Optional<User> findByDniAndNumberDocument(TypeDocument typeDocument, int
    // numberDocument);
    @Query("SELECT u FROM User u WHERE u.numberDocument = :numberDocument AND u.typeDocument = :typeDocument")
    Page<User> findByNumberDocumentAndTypeDocument(
            @Param("numberDocument") int numberDocument,
            @Param("typeDocument") TypeDocument typeDocument,
            Pageable pageable);

    // Optional<User> findByPasaporteAndNumberDocument(TypeDocument typeDocument,
    // int numberDocument);

    Optional<User> findByName(String username);
}
