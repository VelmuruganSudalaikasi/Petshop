package com.treasuremount.petshop.Payment.RefundController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundPaymentRequest {
    private List<Long> orderIds;
    private BigDecimal totalAmount;
    private String paymentMode;
    private String paymentReference;
    private String remarks;
}
