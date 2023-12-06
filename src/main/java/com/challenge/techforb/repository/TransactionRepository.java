package com.challenge.techforb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.techforb.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Page<Transaction> findAllBySenderCard_User_IdOrRecipientCard_User_Id(long senderUserId, long recipientUserId, Pageable page);

}
