package com.treasuremount.petshop.DTO;

import com.treasuremount.petshop.PetResource.PetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetFullDetailsDTO  {

   private PetResponseDTO petResponseDTO;
   private VendorRegisterDTO vendorRegisterDTO;

}
