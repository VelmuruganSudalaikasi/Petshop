package com.treasuremount.petshop.Order.Exception;

/**
 * Exception to be thrown when an invalid order operation is performed.
 */

public class InvalidOrderException extends Exception {

    // Default constructor
    public InvalidOrderException() {
        super("Invalid order operation.");
    }

    // Constructor with a custom error message
    public InvalidOrderException(String message) {
        super(message);
    }

    // Constructor with a custom error message and a cause
    public InvalidOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with a cause
    public InvalidOrderException(Throwable cause) {
        super("Invalid order operation caused by another issue.", cause);
    }

    // Constructor for advanced control
    public InvalidOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
