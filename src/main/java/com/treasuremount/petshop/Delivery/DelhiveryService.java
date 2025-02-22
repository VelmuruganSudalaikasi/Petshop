package com.treasuremount.petshop.Delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treasuremount.petshop.Delivery.ClientWarehouseDTO.ClientWarehouseDTO;
import com.treasuremount.petshop.Delivery.ClientWarehouseDTO.ClientWarehouseResponseDTO;
import com.treasuremount.petshop.Delivery.PincodeDTO.DelhiveryResponse;
import com.treasuremount.petshop.Delivery.ShippmentDTO.ShipmentRequestDTO;
import com.treasuremount.petshop.Delivery.ShippmentDTO.ShipmentResponseDTO;
import com.treasuremount.petshop.Delivery.ShippmentResponseDTO.ErrorResponse;
import com.treasuremount.petshop.Delivery.ShippmentResponseDTO.ShipmentResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;




@Service
@Slf4j
public class DelhiveryService {

    private static final Logger logger = LoggerFactory.getLogger(DelhiveryService.class);

    private final DelhiveryConfig delhiveryConfig;
    private final RestTemplate restTemplate;
    private final WebClient webClient;




    @Autowired
    public DelhiveryService(RestTemplate restTemplate,
                            DelhiveryConfig delhiveryConfig,
                            WebClient webClient) {
        this.restTemplate = restTemplate;
        this.delhiveryConfig = delhiveryConfig;
        this.webClient=webClient;
    }

    public List<String> fetchWayBills(Integer count) {
        String url = delhiveryConfig.getBaseUrl() + "waybill/api/bulk/json/?count=" + count;


        return webClient.get()
                .uri(url)
                .headers(httpHeaders -> {
                    httpHeaders.addAll(delhiveryConfig.getHeaders());
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    // Remove escaped quotes and split the response by commas
                    String cleanedResponse = response.replace("\"", ""); // Remove all double quotes
                    return Arrays.asList(cleanedResponse.split(",")); // Split by commas
                })
                .block(); // Block to get the result synchronously

    }


    public ClientWarehouseResponseDTO clientWarehouseCreation(ClientWarehouseDTO clientWarehouseDTO) {

        String url=delhiveryConfig.getBaseUrl()+"api/backend/clientwarehouse/create/";

       return webClient.post()
               .uri(url)
               .headers(httpHeaders -> {
                   httpHeaders.addAll(delhiveryConfig.getHeaders());
                   httpHeaders.add("Content-Type", "application/json");
                   httpHeaders.add("Accept", "application/json");
//                   logHeaders(httpHeaders);
               })
               .bodyValue(clientWarehouseDTO)
               .retrieve()
               .bodyToMono(ClientWarehouseResponseDTO.class)
               .block(); // Blocking to return a synchronous response
   }
    private void logHeaders(HttpHeaders headers) {
        headers.forEach((key, value) -> logger.info("Header -> {}: {}", key, value));
    }

    // Method to create a shipment
    public Mono<ShipmentResponseDTO> createShipment(ShipmentRequestDTO shipmentRequestDTO) {
        String url = delhiveryConfig.getBaseUrl() + "/api/cmu/create.json";
        String data = serializeToJson(shipmentRequestDTO);

        // URL-encode the data to handle any special characters
        String encodedData = UriUtils.encode(data, StandardCharsets.UTF_8);

        // Build the form-encoded body
        String body = "format=json&data=" + encodedData;

        return this.webClient.post()
                .uri(url)
                .headers(httpHeaders -> {
                    httpHeaders.addAll(delhiveryConfig.getHeaders());
                    httpHeaders.add("Content-Type", "application/json");
                    httpHeaders.add("Accept", "application/json");
//                   logHeaders(httpHeaders);
                })
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ShipmentResponseDTO.class);

    }

    private String serializeToJson(ShipmentRequestDTO shipmentRequestDTO) {
        // Serialize the object into a JSON string (You can use Jackson or any other library)
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(shipmentRequestDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing ShipmentRequestDTO", e);
        }
    }



/*
  public String createShipment(ShipmentRequest shipmentRequest) {
        String url = delhiveryConfig.getBaseUrl() + "/createShipment";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + delhiveryConfig.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ShipmentRequest> requestEntity = new HttpEntity<>(shipmentRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response.getBody();
    }
*/

    // Method to generate a shipping label
    public byte[] generateLabel(String waybillNumber) {
        String url = delhiveryConfig.getBaseUrl() + "/generateLabel?waybill=" + waybillNumber;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + delhiveryConfig.getApiKey());

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, byte[].class);

        return response.getBody();
    }

    // Method to track a shipment
    public ResponseEntity<?> trackShipment(String waybillNumber) {
        String url = delhiveryConfig.getBaseUrl() + "api/v1/packages/json/?"+waybillNumber;


        String rawResponse= webClient.get()
                .uri(url)
                .headers(httpHeaders -> {
                    httpHeaders.addAll(delhiveryConfig.getHeaders());
                    httpHeaders.add("Content-Type", "application/json");
                    httpHeaders.add("Accept", "application/json");
//                   logHeaders(httpHeaders);
                })
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Blocking to return a synchronous response


        ObjectMapper mapper = new ObjectMapper();
        try {
             ShipmentResponse response=mapper.readValue(rawResponse, ShipmentResponse.class);
             return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            try{
                ErrorResponse response=mapper.readValue(rawResponse, ErrorResponse.class);
               return  ResponseEntity.ok(response);
            }catch (JsonProcessingException ex){

                System.out.println("Mapping error: " + ex.getMessage());
                throw new RuntimeException("Error mapping response", ex);
            }

        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }



    }


    public Mono<DelhiveryResponse> isPincodeServiceable(String pinCode) {
        String url = delhiveryConfig.getBaseUrl()+"c/api/pin-codes/json/";
        if(pinCode != null){
            url+= "?filter_codes=" + pinCode;
        }
        return webClient.get()
                .uri(url)
                .headers((httpHeaders) ->httpHeaders.addAll(delhiveryConfig.getHeaders()))
                .retrieve()
                .bodyToMono(DelhiveryResponse.class);

    }


    // Calculate shipping charge (example logic)
    public Mono<BigDecimal> getTotalShippingCharge(String sourcePinCode,
                                                   String destinationPinCode,
                                                   Double weight
    ) {

        if(weight < 0){
         throw  new RuntimeException("Weight must be greater that zero");
        }
        String md="S";
         Double cgm=weight;
        String o_pin=sourcePinCode;
        String d_pin=destinationPinCode;
        String ss="Delivered";


/*
* ?md=S&ss=Delivered&d_pin=&o_pin=&cgm=&pt=&cod=0" \
     --header "Content-Type:application/json"\
     * */

        String url = delhiveryConfig.getBaseUrl() +
                "api/kinko/v1/invoice/charges/.json?" +
                "md="+ md +"&ss=" + ss +"&d_pin=" + d_pin + "&o_pin=" + o_pin+"&cgm=" + cgm
                +"&pt=Pre-paid"
                +"&cod=0";
log.info("URL FOR THE SERVICE {}", url);

/*
* https://staging-express.delhivery.com/api/kinko/v1/invoice/charges/.json?md=S&ss=Delivered&d_pin=600026&o_pin=600001&cgm=10000&pt=Pre-paid&cod=0
md=S&ss=Delivered&d_pin=600027&o_pin=600001&cgm=1000

https://staging-express.delhivery.com/c/api/kinko/v1/invoice/charges/.json*/


        return webClient.get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(delhiveryConfig.getHeaders()))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> jsonNode.isArray() && jsonNode.size() > 0
                        ? new BigDecimal(Math.max(0, jsonNode.get(0).path("total_amount").asDouble(0)))
                        : BigDecimal.ZERO);
    }
}