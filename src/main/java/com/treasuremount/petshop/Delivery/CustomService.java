package com.treasuremount.petshop.Delivery;


import com.treasuremount.petshop.Delivery.ClientWarehouseDTO.ClientWarehouseDTO;
import com.treasuremount.petshop.Delivery.ClientWarehouseDTO.ClientWarehouseResponseDTO;
import com.treasuremount.petshop.Delivery.PincodeDTO.DelhiveryResponse;
import com.treasuremount.petshop.Delivery.PincodeDTO.PostalCode;
import com.treasuremount.petshop.Delivery.ShippmentDTO.OrderInfoService;
import com.treasuremount.petshop.Delivery.ShippmentDTO.OrderInformation;
import com.treasuremount.petshop.Delivery.ShippmentDTO.ShipmentRequestDTO;
import com.treasuremount.petshop.Delivery.ShippmentDTO.ShipmentResponseDTO;
import com.treasuremount.petshop.Entity.ShippingAddress;
import com.treasuremount.petshop.Entity.Vendor;
import com.treasuremount.petshop.Order.Entity.Orders;
import com.treasuremount.petshop.Order.Repository.OrdersRepo;
import com.treasuremount.petshop.Order.Service.OrderServiceImpl;
import com.treasuremount.petshop.Service.ShippingService;
import com.treasuremount.petshop.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Service
public class CustomService {

    private static  final String PAYMENT_MODE="Prepaid";

    @Autowired
    DelhiveryService delhiveryService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private ShippingService userShippingService;

    private final static String SHIPMENT_MODE="Surface";



   //create the warehouse for vendor

    final static String CREATED_MSG="Client Warehouse Created Successfully";
    final static String SHIPMENT_MSG="Created Successfully";


    public String createWareHouse(ClientWarehouseDTO clientWarehouseDTO) {
        ClientWarehouseResponseDTO responseDTO=null;
        try{
             responseDTO= delhiveryService.clientWarehouseCreation(clientWarehouseDTO);
            System.out.println(responseDTO);
        }catch (Exception e){
            e.printStackTrace();
        }
       if(!responseDTO.getSuccess()){
           return responseDTO.getError();
       }
       return CREATED_MSG;
    }


    private ShipmentRequestDTO MockWithDummyData(ShipmentRequestDTO dto){

        /*
        * "pickup_location": {
    "name": "sankarshop",
    "add": "1234 Main Street",
    "city": "Metropolis",
    "pin_code": 600026,
    "country": "India",
    "phone": "03284093280"
  }
        * */


        if (dto.getPickup_location() == null) {
            dto.setPickup_location(new ShipmentRequestDTO.PickupLocation());
        }
        String wayBill=delhiveryService.fetchWayBills(1).get(0);
        System.out.println("FETCHED THE WAY BILL AUTOMATICALLY"+ wayBill);
        dto.getShipments().get(0).setWaybill(wayBill);
        dto.getShipments().get(0).setShipping_mode(SHIPMENT_MODE);
        ShipmentRequestDTO.PickupLocation mockedLocation= dto.getPickup_location();
        mockedLocation.setName("sankarshop");
        mockedLocation.setAdd("1234 Main Street");
        mockedLocation.setCity("Metropolis");
        mockedLocation.setPin_code("600026");
        mockedLocation.setPhone("03284093280");
        return dto;

    }

    private ShipmentRequestDTO mappingWithNecessaryDetails(ShipmentRequestDTO dto,ShippingAddress userAddress){
        ShipmentRequestDTO.Shipment shipment= dto.getShipments().get(0);
        String name = userAddress.getFullName();
        String add=userAddress.getAddressLine1();
        String pin=userAddress.getPostalCode();
        String phone=userAddress.getPhoneNumber();
        String city=userAddress.getCity();
        shipment.setCity(city);
        shipment.setPhone(phone);
        shipment.setName(name);
        shipment.setAdd(add);
        shipment.setPin(pin);

        shipment.setPayment_mode(PAYMENT_MODE);

        return dto;

    }

    public OrderInformation InitiateShipping(Long orderId) {
        Orders order = ordersRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Create a new ShipmentRequestDTO instance
        ShipmentRequestDTO dto = new ShipmentRequestDTO();

        // Ensure shipments list is initialized
        if (dto.getShipments() == null) {
            dto.setShipments(new ArrayList<>()); // Initialize the list
        }

        // Create a new Shipment object and add it to the list
        ShipmentRequestDTO.Shipment shipment = new ShipmentRequestDTO.Shipment();
        dto.getShipments().add(shipment); // Now index 0 is valid

        // Fetch the shipping address and map details
        ShippingAddress address = userShippingService.getOneById(order.getShippingAddressId());
        mappingWithNecessaryDetails(dto, address); // Now this won't break

        // Set order ID
        dto.getShipments().get(0).setOrder(order.getUniqueOrderId());

        // ✅ Convert orderDate safely
        Date orderDate = order.getOrderDate();
        Instant instant;

        if (orderDate instanceof java.sql.Date) {
            instant = ((java.sql.Date) orderDate).toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant();
        } else {
            instant = orderDate.toInstant(); // Works fine for java.util.Date
        }

        // Format Instant to ISO 8601
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(ZoneId.of("UTC"));

        String formattedDate = formatter.format(instant);
        System.out.println("Formatted Order Date: " + formattedDate);

        dto.getShipments().get(0).setOrder_date(formattedDate);

        return ShipmentCreation(dto);
    }


    public OrderInformation ShipmentCreation(ShipmentRequestDTO dto){
        MockWithDummyData(dto);
        ShipmentResponseDTO responseDTO=delhiveryService.createShipment(dto).block();
        System.out.println(responseDTO);
        OrderInformation information=new OrderInformation();
        OrderInformation information1=information.mapToOrderInformation(responseDTO);
        return orderInfoService.create(information1);

    }

    //check
    // if the prepaid and remarks is empty means i go with order placement
    // before we want to create the warehouse for vendor pass the vendor address


    public BigDecimal CalculateCost(
                                    Long ProductId,
                                    String destinationPinCode,
                                    Double weight){

         Vendor productVendor= vendorService.findVendorByProductId(ProductId);
         String sourcePinCode=productVendor.getPostalCode();

         if(checkPickUp(isServiceable(sourcePinCode)) && checkPrePaidAndRemarks(isServiceable(destinationPinCode))){
            Mono<BigDecimal> responseMono=delhiveryService.getTotalShippingCharge(sourcePinCode,destinationPinCode,weight);
            return responseMono.blockOptional().get();
        }else {

            throw  new RuntimeException("Service not Available for the Area  OR SRC " + sourcePinCode +"Dest"+ destinationPinCode );
        }

    }



    public PostalCode isServiceable(String pinCode){
        Mono<DelhiveryResponse> responseMono=delhiveryService.isPincodeServiceable(pinCode);
        PostalCode postalCode= Objects.requireNonNull(responseMono.block()).getDeliveryCodes().get(0).getPostalCode();
        return postalCode;

    }

    public Boolean checkPrePaidAndRemarks(PostalCode postalCode){
        String prePaid=postalCode.getPrePaid();
        String remarks=postalCode.getRemarks();
        System.out.println("prePaid" + prePaid);
        System.out.println("remarks" + remarks);
        if(prePaid ==null || remarks ==null) return false;
        boolean y = prePaid.equalsIgnoreCase("Y") && remarks.isEmpty();
        return y;

    }

    public Boolean checkPickUp(PostalCode postalCode){
        String pickup=postalCode.getPickup();
        String repl = postalCode.getRepl();
        boolean result=true;
        result &= checkValid(pickup,"Y");
        result &= checkValid(repl,"Y");
        return result;
    }

    private Boolean checkValid(String content,String response){
        boolean  y= (content != null) && (content.equalsIgnoreCase(response));
        if(!y){
            System.out.println("Returns the false for " + content +" " + response);
        }
        return y;
    }







}
