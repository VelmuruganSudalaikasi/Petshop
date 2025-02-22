package com.treasuremount.petshop.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductStatusDTO {


    private Long productId;

    private Long ProductStatusId;
}
