package com.treasuremount.petshop.Payment.RefundController;
import com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO;
import com.treasuremount.petshop.Payment.VendorPayment.OrderDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments/refund")
@Slf4j
public class RefundController {

    @Autowired
    RefundService refundService;

    @PostMapping("/pay")
    public ResponseEntity<RefundPayment> processPayment(@RequestBody RefundPaymentRequest paymentRequest) {
        RefundPayment payment = refundService.processVendorPayment(paymentRequest);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/allVendorStatus")
    public ResponseEntity<List<VendorPaymentDTO>> getAllVendors(@RequestParam(value = "isPaid",defaultValue = "1") Boolean isPaid){
        return ResponseEntity.ok(refundService.getAllVendors(isPaid));
    }

    @GetMapping("/getAll/{vendorId}")
    public ResponseEntity<List<OrderDetailDTO>> processPayment(@PathVariable("vendorId") Long userId) {
        List<OrderDetailDTO> payment = refundService.getAllOrderWithDetails(userId);
        return ResponseEntity.ok(payment);
    }

}
