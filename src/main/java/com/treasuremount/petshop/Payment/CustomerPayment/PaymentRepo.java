package com.treasuremount.petshop.Payment.CustomerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

}