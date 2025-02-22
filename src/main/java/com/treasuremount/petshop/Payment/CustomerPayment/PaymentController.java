package com.treasuremount.petshop.Payment.CustomerPayment;
import com.treasuremount.petshop.Payment.VendorPayment.PaymentRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/makePayment")
    public ResponseEntity<List<Payment>> makePayment(@RequestBody List<Payment> payment) {
       List<Payment> savedPayment = paymentService.saveAll(payment);
        return ResponseEntity.ok(savedPayment);
    }



    // Other endpoints as needed
}
