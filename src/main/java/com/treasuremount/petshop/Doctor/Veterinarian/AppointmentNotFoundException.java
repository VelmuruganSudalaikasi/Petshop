package com.treasuremount.petshop.Doctor.Veterinarian;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
