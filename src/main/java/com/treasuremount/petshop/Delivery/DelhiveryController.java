package com.treasuremount.petshop.Delivery;
import com.treasuremount.petshop.Delivery.ClientWarehouseDTO.ClientWarehouseDTO;
import com.treasuremount.petshop.Delivery.ShippmentDTO.OrderInformation;
import com.treasuremount.petshop.Delivery.ShippmentDTO.ShipmentRequestDTO;
import com.treasuremount.petshop.Delivery.ShippmentResponseDTO.ShipmentResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/*
*
*  Flow
*   calculate the estimate cost and sent to backend
*    I will fetch the way bill initiate the orderId+WayBill
*    With Get the userDetails and the vendor details and pass it to the createShipment
*    After the tracking can be made via the way bill or the OrderId  will shown in normally
*
* */

@RestController
@RequestMapping("/api/public/delhivery")
public class DelhiveryController {

    @Autowired
    private DelhiveryService delhiveryService;



    @Autowired
    private CustomService customService;

    /*curl --request GET \
     --url "https://staging-express.delhivery.com/waybill/api/bulk/json/?count=5" \
     --header "Content-Type:application/json"\
     --header "Authorization: Token api-token-key Pass Token as 'Token XXXXXXXXXXXXXXXXXX'" \

    * */

    // Endpoint to create a shipment
    @PostMapping("/createShipment")
    public ResponseEntity<OrderInformation> createShipment() {

         return ResponseEntity.ok(customService.InitiateShipping(35L));
    }

    @PostMapping("/createWarehouse")
    public String createWarehouse(@RequestBody @Valid
                                      ClientWarehouseDTO clientWarehouseDTO) {
        return customService.createWareHouse(clientWarehouseDTO);
    }


    @GetMapping("/fetchWayBill")
    public List<String> generateWayBill(@RequestParam Integer count) {
        return delhiveryService.fetchWayBills(count);
    }


    // Endpoint to generate a shipping label
    @GetMapping("/generateLabel")
    public byte[] generateLabel(@RequestParam String waybillNumber) {
        return delhiveryService.generateLabel(waybillNumber);
    }

    // Endpoint to track a shipment
    @GetMapping("/trackShipment")
    public ResponseEntity<?>  trackShipment(@RequestParam String Number,
                                          @RequestParam(required = false,defaultValue = "1") Boolean isOrderId
                                ) {

        String template="";
        if(isOrderId){
            template+="waybill=&ref_ids="+Number;
        }else {
            template+="waybill="+Number+"&ref_ids=";
        }

        return delhiveryService.trackShipment(template);
    }

/*    @GetMapping("/checkServiceability")
    public ResponseEntity<?> checkServiceability(
            @RequestParam String pincode,
            @RequestParam double orderWeight // Optional: Weight for shipping cost
    ) {
        try {
            // Call Delhivery API to check pincode serviceability
            Map<String,Object> isServiceable = delhiveryService.isPincodeServiceable(pincode);
            System.out.println(isServiceable);

            if (!isServiceable.isEmpty()) {
                return ResponseEntity.badRequest().body("Delivery not available at this pincode.");
            }

            // Fetch shipping charges from Delhivery API (based on pincode and weight)
//            double shippingCharge = delhiveryService.calculateShippingCharge(pincode, orderWeight);

            return ResponseEntity.ok(isServiceable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking serviceability.");
        }
    }*/

    @GetMapping("/checkServiceability")
    public ResponseEntity<?> checkServiceability(
            @RequestParam() Long productId,
            @RequestParam() String destinationPinCode,
            @RequestParam() Double orderWeight
    ) {
        try {
              BigDecimal response=customService.CalculateCost(productId,destinationPinCode,orderWeight);
              return  ResponseEntity.ok(response);

        }catch (RuntimeException e){
            return ResponseEntity.ok(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Something went wrong");
        }

    }

}