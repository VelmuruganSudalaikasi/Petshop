package com.treasuremount.petshop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treasuremount.petshop.Delivery.ShippmentResponseDTO.ErrorResponse;
import com.treasuremount.petshop.Delivery.ShippmentResponseDTO.Shipment;
import com.treasuremount.petshop.Delivery.ShippmentResponseDTO.ShipmentData;
import com.treasuremount.petshop.Delivery.ShippmentResponseDTO.ShipmentResponse;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentResponseMappingTest {



    @Test
    void TestResponse() throws JsonProcessingException {

        ShipmentResponseMappingTest test=new ShipmentResponseMappingTest();
        ResponseEntity<?> response=test.testObjectMapperMapping();
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

    }


    ResponseEntity<?> testObjectMapperMapping() throws JsonProcessingException {
        // Arrange
        String rawResponse = "{\"ShipmentData\":[{\"Shipment\":{\"AWB\":\"84209310000302\",\"CODAmount\":0,\"ChargedWeight\":null,\"Consignee\":{\"Address1\":[],\"Address2\":[],\"Address3\":\"\",\"City\":\"Metropolis\",\"Country\":\"India\",\"Name\":\"dogchain\",\"PinCode\":600045,\"State\":\"Tamil Nadu\",\"Telephone1\":\"\",\"Telephone2\":\"\"},\"DeliveryDate\":null,\"DestRecieveDate\":null,\"Destination\":\"Metropolis\",\"DispatchCount\":0,\"Ewaybill\":[],\"ExpectedDeliveryDate\":null,\"Extras\":\"\",\"FirstAttemptDate\":null,\"InvoiceAmount\":0,\"OrderType\":\"Pre-paid\",\"Origin\":\"Chennai_Chrompet_DPC (Tamil Nadu)\",\"OriginRecieveDate\":null,\"OutDestinationDate\":null,\"PickUpDate\":\"2025-02-15T17:47:35.835\",\"PickedupDate\":null,\"PickupLocation\":\"surandai\",\"PromisedDeliveryDate\":null,\"Quantity\":\"\",\"RTOStartedDate\":null,\"ReferenceNo\":\"ORD5982349\",\"ReturnPromisedDeliveryDate\":null,\"ReturnedDate\":null,\"ReverseInTransit\":false,\"Scans\":[{\"ScanDetail\":{\"Instructions\":\"Shipment details manifested\",\"Scan\":\"Manifested\",\"ScanDateTime\":\"2025-02-15T17:47:35.861\",\"ScanType\":\"UD\",\"ScannedLocation\":\"Chennai_Chrompet_DPC (Tamil Nadu)\",\"StatusCode\":\"X-UCI\",\"StatusDateTime\":\"2025-02-15T17:47:35.861\"}}],\"SenderName\":\"e8f3c1-TreasureMount-do-cdp\",\"Status\":{\"Instructions\":\"Shipment details manifested\",\"RecievedBy\":\"\",\"Status\":\"Manifested\",\"StatusCode\":\"X-UCI\",\"StatusDateTime\":\"2025-02-15T17:47:35.861\",\"StatusLocation\":\"Chennai_Chrompet_DPC (Tamil Nadu)\",\"StatusType\":\"UD\"}}}]}";
rawResponse="{\n" +
        "    \"rmk\": \"Some error has occurred. Please contact client.support@delhivery.com with error message- No such waybill or Order Id found\",\n" +
        "    \"Success\": false,\n" +
        "    \"Error\": \"No such waybill or Order Id found\"\n" +
        "}";
        ObjectMapper mapper = new ObjectMapper();

        // Act
        try{

            ShipmentResponse shipmentResponse = mapper.readValue(rawResponse, ShipmentResponse.class);
            System.out.println("shipmentResponse " + shipmentResponse);
            return ResponseEntity.ok(shipmentResponse);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            try{

                ErrorResponse response=mapper.readValue(rawResponse,ErrorResponse.class);
                System.out.println("shipmentResponse " + response);
                return ResponseEntity.ok(response);
            }catch (JsonProcessingException ex){
                ex.printStackTrace();

            }
        }
      return ResponseEntity.notFound().build();

    }
}