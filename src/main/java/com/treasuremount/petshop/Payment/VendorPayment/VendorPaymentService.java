package com.treasuremount.petshop.Payment.VendorPayment;

import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class VendorPaymentService {
    @Autowired
    private VendorPaymentRepo vendorPaymentRepo;

    @Autowired
    private OrdersRepo ordersRepo;

    public VendorPayment processVendorPayment(PaymentRequestDTO paymentRequest) {
        // Create new payment record
        VendorPayment payment = new VendorPayment();
        payment.setTotalAmount(paymentRequest.getTotalAmount());
        payment.setPaymentMode(paymentRequest.getPaymentMode());
        payment.setRemarks(paymentRequest.getRemarks());
        payment.setPaymentReference(paymentRequest.getPaymentReference());

        // Save payment first
        VendorPayment savedPayment = vendorPaymentRepo.save(payment);

        // Update orders with payment reference
        vendorPaymentRepo.updateOrdersWithPayment(
                paymentRequest.getOrderIds(),
                savedPayment
        );
        vendorPaymentRepo.updateVendorDeductionStatus(paymentRequest.getDeductionOrderIds());

        return savedPayment;
    }


/*
    public void processRefundPayment(RefundPaymentDTO paymentRequest) {
        // Create new payment record
//        VendorPayment payment = new VendorPayment();
//        payment.setTotalAmount(paymentRequest.getTotalAmount());
//        payment.setPaymentMode(paymentRequest.getPaymentMode());
//        payment.setRemarks(paymentRequest.getRemarks());
//        payment.setPaymentReference(paymentRequest.getPaymentReference());

        // Save payment first
//        VendorPayment savedPayment = vendorPaymentRepo.save(payment);

        // Update orders with payment reference
        vendorPaymentRepo.updateOrdersWithPayment(
                paymentRequest.getOrderIds(),
                savedPayment
        );
        vendorPaymentRepo.updateVendorDeductionStatus(paymentRequest.getDeductionOrderIds());

        return savedPayment;
    }*/


 /*   public List<OrderDetailsAllDTO> getUnpaidVendorOrders(Long id,Long vendorId ) {

        List<OrderDetailDTO> vendorPaymentDTOS=vendorPaymentRepo.findAllByVendorIdWithNoVendorPayment(vendorId,);
        List<OrderDetailDTO> cancelOrReturnList=vendorPaymentRepo.findCancelOrReturnOrdersByVendorUserId(vendorUserId);

        OrderDetailsAllDTO returnLst=new OrderDetailsAllDTO();
        returnLst.setPaymentList(vendorPaymentDTOS);
        returnLst.setCancelOrRetrunList(cancelOrReturnList);
    }*/

        public List<OrderDetailDTO> getVendorOrdersWithReturnsOrCancellations(Long vendorUserId) {
            return vendorPaymentRepo.findCancelOrReturnOrdersByVendorUserId(vendorUserId);
        }




    public VendorPayment getOneById(Long id){
        return vendorPaymentRepo.findById(id).orElseThrow(null);
    }

    public List<VendorPayment> getAll(){
        return vendorPaymentRepo.findAll();
    }

    public List<VendorPaymentDTO> getAllVendors(Boolean isPaid) {
        return vendorPaymentRepo.allVendorStatus(isPaid);
    }

    public OrderDetailsAllDTO getAllOrderWithDetails(Long orderStatusId,Long vendorId){



            List<OrderDetailDTO> vendorPaymentDTOS= vendorPaymentRepo.findAllByVendorIdWithNoVendorPayment(vendorId,orderStatusId);;
            List<OrderDetailDTO> cancelOrReturnList=vendorPaymentRepo.findCancelOrReturnOrdersByVendorUserId(vendorId);

            OrderDetailsAllDTO returnLst=new OrderDetailsAllDTO();
            returnLst.setPaymentList(vendorPaymentDTOS);
            returnLst.setCancelOrRetrunList(cancelOrReturnList);


        return  returnLst;
    }

   /* public List<VendorConfirmationDTO> getAllUnConfirmedPayments(Long vendorId, Boolean isConfirmed) {
        return vendorPaymentRepo.getVendorPaymentsWithUniqueOrderIds(vendorId, isConfirmed);
    }
*/
    @Transactional
    public void updateVendorConfirmation(List<Long> vendorIds) {
        vendorPaymentRepo.confirmVendorPaymentsByIds(vendorIds); // Adjust the repo method to use uniqueOrderId
    }

    public List<VendorConfirmationDTO> getAllUnConfirmedPayments(Long vendorId, Boolean isConfirmed) {
        List<Object[]> results = vendorPaymentRepo.getVendorPaymentsWithUniqueOrderIds(vendorId, isConfirmed);
        List<VendorConfirmationDTO> vendorConfirmationDTOs = new ArrayList<>();

        for (Object[] result : results) {
            Long id = (Long) result[0];
            String uniqueOrderIds = (String) result[1]; // The comma-separated string
            BigDecimal totalAmount = (BigDecimal) result[2];
            Date paymentDate = (Date) result[3];
            String paymentReference = (String) result[4];
            String paymentMode = (String) result[5];
            String remarks = (String) result[6];
            Boolean vendorConfirmation = (Boolean) result[7];

            VendorConfirmationDTO dto = new VendorConfirmationDTO(id, uniqueOrderIds, totalAmount, paymentDate,
                    paymentReference, paymentMode, remarks, vendorConfirmation);

            vendorConfirmationDTOs.add(dto);
        }

        return vendorConfirmationDTOs;
    }


    @Transactional
  public void   markVendorDeductionsAsDone(List<Long> orderIds){
        vendorPaymentRepo.updateVendorDeductionStatus(orderIds);

  }





}
