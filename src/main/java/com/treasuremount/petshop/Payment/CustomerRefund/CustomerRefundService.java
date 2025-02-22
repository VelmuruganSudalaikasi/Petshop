package com.treasuremount.petshop.Payment.CustomerRefund;


import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO;
import com.treasuremount.petshop.Payment.VendorPayment.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerRefundService {
    @Autowired
    private CustomerPaymentRepo customerPaymentRepo;

    @Autowired
    private OrdersRepo ordersRepo;

    public CustomerPayment processVendorPayment(PaymentRequestDTO paymentRequest) {
        // Create new payment record
        CustomerPayment payment = new CustomerPayment();
        payment.setTotalAmount(paymentRequest.getTotalAmount());
        payment.setPaymentMode(paymentRequest.getPaymentMode());
        payment.setRemarks(paymentRequest.getRemarks());
        payment.setPaymentReference(paymentRequest.getPaymentReference());

        // Save payment first
        CustomerPayment savedPayment = customerPaymentRepo.save(payment);

        // Update orders with payment reference
        customerPaymentRepo.updateOrdersWithPayment(
                paymentRequest.getOrderIds(),
                savedPayment
        );

        return savedPayment;
    }

    public List<CustomerPaymentDTO> getUnpaidVendorOrders(Long id) {
        return customerPaymentRepo.findUnpaidVendorOrders(id);
    }
/*
    public List<OrdersDTO> getReturnedAndCancelProducts() {
        // Fetch the list of cancelled or returned orders
        List<Orders> lstOfOrders = ordersRepo.findCancelAndReturnOrders();

        // Create a list to store the DTOs
        List<OrdersDTO> lstOfOrdersDTO = new ArrayList<>();

        // Loop through the Orders and map each one to an OrdersDTO
        for (Orders order : lstOfOrders) {
            OrdersDTO ordersDTO = new OrdersDTO();

            // Map fields from Orders to OrdersDTO
            ordersDTO.setId(order.getId());
            ordersDTO.setUserId(order.getUserId());
            ordersDTO.setProductId(order.getProductId());
            ordersDTO.setUnitPrice(order.getUnitPrice());
            ordersDTO.setQuantity(order.getQuantity());
            ordersDTO.setTax(order.getTax());
            ordersDTO.setShippingCharge(order.getShippingCharge());
            ordersDTO.setSubtotal(order.getSubtotal());
            ordersDTO.setOrderStatusId(order.getOrderStatusId());
            ordersDTO.setTotalAmount(order.getTotalAmount());
            ordersDTO.setShippingAddress(order.getShippingAddress());
            ordersDTO.setShippingAddressId(order.getShippingAddressId());
            ordersDTO.setOrderDate(order.getOrderDate());
            ordersDTO.setDeliveryDate(order.getDeliveryDate());
            ordersDTO.setVendorPaymentId(order.getVendorPayment() != null ? order.getVendorPayment().getId() : null);
            ordersDTO.setIsCancelled(order.getCancelled());
            ordersDTO.setIsReturned(order.getReturned());
            ordersDTO.setReturnAmountIsPaid(order.getReturnAmountIsPaid());


            // Add the DTO to the list
            lstOfOrdersDTO.add(ordersDTO);
        }

        // Return the list of mapped OrdersDTO objects
        return lstOfOrdersDTO;
    }

    public VendorPayment getOneById(Long id){
        return vendorPaymentRepo.findById(id).orElseThrow(null);
    }

    public List<VendorPayment> getAll(){
        return vendorPaymentRepo.findAll();
    }*/

    public List<CustomerPaymentDTO> getAllVendors(Boolean isPaid) {
        return customerPaymentRepo.allVendorStatus(isPaid);
    }

    public List<OrderDetailDTO> getAllOrderWithDetails(Long userId,Long orderStatusId){
        return customerPaymentRepo.findOrderDetailsByUserIdAndReturned(userId,orderStatusId);
    }




}
