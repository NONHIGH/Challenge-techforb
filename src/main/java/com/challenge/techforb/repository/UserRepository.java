package com.challenge.techforb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.techforb.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
