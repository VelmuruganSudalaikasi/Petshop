package com.treasuremount.petshop.Delivery.ShippmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uniqueOrderId;

    private Boolean success;

    private String waybill;

    private List<String> remarks;

    private String upload_wbn;


    public  OrderInformation mapToOrderInformation(ShipmentResponseDTO dto){
        ShipmentResponseDTO.Package pack=dto.getPackages().get(0);
        this.uniqueOrderId=pack.getRefnum();
        this.success=dto.getSuccess();
        this.waybill=pack.getWaybill();
        List<String> remarks=new ArrayList<>();

        for(String str: pack.getRemarks()){
            boolean add = remarks.add(str);
        }
        this.remarks=remarks;

        this.upload_wbn=dto.getUpload_wbn();

        return this;

    }

}
