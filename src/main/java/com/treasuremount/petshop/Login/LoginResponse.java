package com.treasuremount.petshop.Login;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    private Long userId;
    private Integer roleId;
    private Boolean activeStatus;
    private String emailId;
    private String token;
    private Long vendorId;
    private Long veterinarianId;


}
