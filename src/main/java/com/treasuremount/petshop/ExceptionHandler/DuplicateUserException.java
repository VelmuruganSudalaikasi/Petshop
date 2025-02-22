package com.treasuremount.petshop.ExceptionHandler;


public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
