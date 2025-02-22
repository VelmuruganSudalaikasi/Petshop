package com.treasuremount.petshop.Payment.VendorPayment;
import com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class VendorPaymentController {
    @Autowired
    private VendorPaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<VendorPayment> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        VendorPayment payment = paymentService.processVendorPayment(paymentRequest);
        return ResponseEntity.ok(payment);
    }




/*
    @GetMapping("/unpaid")
    public ResponseEntity<List<VendorPaymentDTO>> getUnpaidOrders(@RequestParam("OrderStatusId") Long orderStatusId) {
        return ResponseEntity.ok(paymentService.getUnpaidVendorOrders(orderStatusId));
    }*/

    @GetMapping("/allVendorStatus")
    public ResponseEntity<List<VendorPaymentDTO>> getAllVendors(@RequestParam(value = "isPaid",defaultValue = "1") Boolean isPaid){
        return ResponseEntity.ok(paymentService.getAllVendors(isPaid));
    }
/*
    @GetMapping("/returnsOrCancellations/{vendorId}")
    public ResponseEntity<OrderDetailsAllDTO> getVendorOrders(@PathVariable Long vendorId) {
        return ResponseEntity.ok(paymentService.getVendorOrdersWithReturnsOrCancellations(vendorId));
    }*/

//    @PostMapping("/markDeductionDone")
//    public ResponseEntity<Void> markVendorDeductionDone(@RequestBody List<Long> orderIds) {
//        paymentService.markVendorDeductionsAsDone(orderIds);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/getAll/{vendorId}")
    public ResponseEntity<OrderDetailsAllDTO> processPayment(@PathVariable("vendorId") Long userId,@RequestParam Long orderStatusId) {
        OrderDetailsAllDTO payment = paymentService.getAllOrderWithDetails(orderStatusId,userId);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("getAll/unConfirmed/{userId}")
    public ResponseEntity<List<VendorConfirmationDTO>> getAllUnConfirmedPayment(@PathVariable("userId") Long userId,
                                                                                @RequestParam(value = "isConfirmed",defaultValue = "0") Boolean confirmed){
       List<VendorConfirmationDTO> unConfirmedPayments=paymentService.getAllUnConfirmedPayments(userId,confirmed);
        return ResponseEntity.ok(unConfirmedPayments);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmVendorPayments(@RequestBody List<Long> vendorPaymentIds) {
        try {
            paymentService.updateVendorConfirmation(vendorPaymentIds);
            return ResponseEntity.ok("Vendor payments confirmed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to confirm vendor payments: " + e.getMessage());
        }
    }





}