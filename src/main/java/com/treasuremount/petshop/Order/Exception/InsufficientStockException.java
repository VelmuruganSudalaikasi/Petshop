package com.treasuremount.petshop.Order.Exception;

public class InsufficientStockException extends Exception {

    // Default constructor
    public InsufficientStockException() {
        super("Insufficient stock operation.");
    }

    // Constructor with a custom error message
    public InsufficientStockException(String message) {
        super(message);
    }

    // Constructor with a custom error message and a cause
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with a cause
    public InsufficientStockException(Throwable cause) {
        super("Insufficient stock  caused by another issue.", cause);
    }

    // Constructor for advanced control
    public InsufficientStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
