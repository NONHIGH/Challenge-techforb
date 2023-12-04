package com.challenge.techforb.exceptions.transactions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message){
        super(message);
    }
}
