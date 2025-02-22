package com.treasuremount.petshop.Payment.CustomerPayment;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepository;

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    public List<Payment> saveAll(List<Payment> paymentList){
        return paymentRepository.saveAll(paymentList);
    }

  /*  public List<VendorPaymentDTO> getUnpaidVendorOrders() {
        return paymentRepository.findUnpaidVendorOrders();
    }

    @Transactional
    public int processVendorPayments(List<Long> orderIds) {
        return paymentRepository.updateOrdersAsPaid(orderIds);
    }*/

    // Other methods as needed
}
