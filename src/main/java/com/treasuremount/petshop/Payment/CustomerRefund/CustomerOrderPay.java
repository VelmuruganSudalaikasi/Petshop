package com.treasuremount.petshop.Payment.CustomerRefund;
import com.treasuremount.petshop.Payment.VendorPayment.OrderDetailDTO;
import com.treasuremount.petshop.Payment.VendorPayment.PaymentRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderPay")
@Slf4j
public class CustomerOrderPay {
    @Autowired
    private CustomerRefundService customerRefundService;


    @PostMapping("/pay")
    public ResponseEntity<CustomerPayment> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        CustomerPayment payment = customerRefundService.processVendorPayment(paymentRequest);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/unpaid")
    public ResponseEntity<List<CustomerPaymentDTO>> getUnpaidOrders(@RequestParam("OrderStatusId") Long orderStatusId) {
        return ResponseEntity.ok(customerRefundService.getUnpaidVendorOrders(orderStatusId));
    }

    @GetMapping("/allVendorStatus")
    public ResponseEntity<List<CustomerPaymentDTO>> getAllVendors(@RequestParam(value = "isPaid",defaultValue = "1") Boolean isPaid){
        return ResponseEntity.ok(customerRefundService.getAllVendors(isPaid));
    }


    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<OrderDetailDTO>> processPayment(@PathVariable("userId") Long userId,@RequestParam("OrderStatusId") Long orderStatusId) {
        List<OrderDetailDTO> payment = customerRefundService.getAllOrderWithDetails(userId,orderStatusId);
        return ResponseEntity.ok(payment);
    }



}
