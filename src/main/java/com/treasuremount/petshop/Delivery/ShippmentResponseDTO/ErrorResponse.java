package com.treasuremount.petshop.Delivery.ShippmentResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
     @JsonProperty("rmk")
     private String  rmk;
     @JsonProperty("Success")
     private boolean Success;
     @JsonProperty("Error")
     private String Error;

}
