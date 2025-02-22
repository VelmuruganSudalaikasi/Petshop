package com.treasuremount.petshop.Delivery.ClientWarehouseDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/*
   *
   * name": "ghgf",
     "email": "",
     "phone": "",
     "address": "",
     "city": "",
     "country": "India",
     "pin": "",
     "return_address": "",
     "return_pin": "",
     "return_city": "",
     "return_state": "",
     "return_country": "India"
   * */


/*
*
* {
  "name": "string",
  "email": "string",
  "phone": "string",
  "address": "string",
  "city": "string",
  "country": "string",
  "pin": "string",
  "returnAddress": "string",
  "returnPin": "string",
  "returnCity": "string",
  "returnState": "string",
  "returnCountry": "string"
}
*
*
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientWarehouseDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "PIN code is required")
    @Size(min = 6, max = 6, message = "PIN code must be 6 digits")
    private String pin;

    @NotBlank(message = "Return address is required")
    private String return_address;

    @NotBlank(message = "Return PIN is required")
    @Size(min = 6, max = 6, message = "Return PIN must be 6 digits")
    private String return_pin;

    @NotBlank(message = "Return city is required")
    private String return_city;

    @NotBlank(message = "Return state is required")
    private String return_state;

    @NotBlank(message = "Return country is required")
    private String return_country;
}
