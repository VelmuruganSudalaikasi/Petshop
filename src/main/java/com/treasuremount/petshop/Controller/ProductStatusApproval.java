package com.treasuremount.petshop.Controller;

import com.treasuremount.petshop.DTO.ProductStatusDTO;
import com.treasuremount.petshop.Service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/public/productApproval")
public class ProductStatusApproval {

    @Autowired
    private  ProductServiceImpl productService;

    @PostMapping("/update")
    public ResponseEntity<?> updateStatus(@RequestBody ProductStatusDTO dto){
        try{
            productService.updateStatus(dto);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.err.println(e);
        }
        return ResponseEntity.notFound().build();
    }

}
