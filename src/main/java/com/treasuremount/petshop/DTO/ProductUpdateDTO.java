package com.treasuremount.petshop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {
    private Double price;
    private Long stockQuantity;
    private Double discount;
    private Integer minStockLevel;
/*
    private BigDecimal tax;
    private BigDecimal shippingCharge;
*/


}
