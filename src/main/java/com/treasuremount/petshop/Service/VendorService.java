package com.treasuremount.petshop.Service;

import com.treasuremount.petshop.DTO.VendorInfoDTO;
import com.treasuremount.petshop.DTO.VendorRegisterDTO;
import com.treasuremount.petshop.Entity.User;
import com.treasuremount.petshop.Entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface VendorService {

        Vendor create(Vendor d);

        Vendor findById(Long id);

        List<Vendor> getAll();

        Vendor getOneUserById(Long id);

        Vendor update(Vendor d, Long id);

        boolean delete(Long id);

        List<VendorInfoDTO> getAllCustom1();

        Vendor findVendorByProductId(Long productId);

//         boolean editVendor(Vendor v);

/*       Page<Vendor> getAllVendors(Pageable pageable);*/


}
