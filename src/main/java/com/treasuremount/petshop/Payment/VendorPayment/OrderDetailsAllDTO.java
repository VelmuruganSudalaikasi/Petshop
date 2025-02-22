package com.treasuremount.petshop.Payment.VendorPayment;

import com.treasuremount.petshop.Payment.CustomerPayment.VendorPaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsAllDTO {
    List<OrderDetailDTO> paymentList;
    List<OrderDetailDTO> cancelOrRetrunList;
}
