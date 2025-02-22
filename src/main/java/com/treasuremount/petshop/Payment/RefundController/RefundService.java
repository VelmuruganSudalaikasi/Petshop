package com.treasuremount.petshop.Payment.RefundController;

import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Order.Service.OrderServiceImpl;
import com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO;
import com.treasuremount.petshop.Payment.VendorPayment.OrderDetailDTO;
import com.treasuremount.petshop.Service.EmailService;
import com.treasuremount.petshop.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefundService {

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    public RefundPayment processVendorPayment(RefundPaymentRequest paymentRequest) {
        // Create new payment record
        RefundPayment payment = new RefundPayment();
        payment.setTotalAmount(paymentRequest.getTotalAmount());
        payment.setPaymentMode(paymentRequest.getPaymentMode());
        payment.setRemarks(paymentRequest.getRemarks());
        payment.setPaymentReference(paymentRequest.getPaymentReference());

        // Save payment first
        RefundPayment savedPayment = refundRepo.save(payment);

        // Update orders with payment reference
        refundRepo.updateOrderWithRefund(
                paymentRequest.getOrderIds(),
                savedPayment
        );
        refundRepo.updateOrderStatusIdToRefund(
                paymentRequest.getOrderIds(),
                OrderServiceImpl.getREFUND()
        );
        sendRefundConfirmationEmail(savedPayment, paymentRequest.getOrderIds());
        return savedPayment;
    }

    // Send Mail Notification for Refund Confirmation
    private void sendRefundConfirmationEmail(RefundPayment refundPayment, List<Long> orderIds) {
        for (Long orderId : orderIds) {
            Orders order = ordersRepo.findById(orderId).orElse(null);
            if (order != null) {
                String userEmail = userService.getUserEmailById(order.getUserId());

                // Construct the email content
                String subject = "Refund Processed - Order #" + order.getUniqueOrderId();
                String body = "Dear Customer,\n\n"
                        + "Your refund request for Order ID: " + order.getUniqueOrderId()
                        + " has been successfully processed.\n\n"
                        + "Refund Amount: $" + refundPayment.getTotalAmount() + "\n"
                        + "Payment Mode: " + refundPayment.getPaymentMode() + "\n"
                        + "Reference: " + refundPayment.getPaymentReference() + "\n\n"
                        + "If you have any questions, feel free to contact our support team.\n\n"
                        + "Thank you for shopping with us!";

                // Send email using EmailService
                emailService.sendEmailNotification(userEmail, subject, body);
            }
        }
    }

    public List<OrderDetailDTO> getAllOrderWithDetails(Long vendorId){
        return refundRepo.payToCustomer(vendorId);
    }

    public List<VendorPaymentDTO> getAllVendors(Boolean isPaid) {
//        return null;
        return refundRepo.allVendorStatus(isPaid);
    }

}
