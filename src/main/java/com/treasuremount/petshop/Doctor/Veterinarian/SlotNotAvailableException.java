package com.treasuremount.petshop.Doctor.Veterinarian;

public class SlotNotAvailableException extends RuntimeException {
    public SlotNotAvailableException(String message) {
        super(message);
    }
}
