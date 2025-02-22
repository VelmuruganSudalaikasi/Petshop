package com.treasuremount.petshop.Delivery.ShippmentResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {
    @JsonProperty("AWB")
    private String awb;
    @JsonProperty("CODAmount")
    private int codAmount;
    @JsonProperty("ChargedWeight")
    private Object chargedWeight;  // Can be null, so Object is used
    @JsonProperty("Consignee")
    private Consignee consignee;
    @JsonProperty("DeliveryDate")
    private String deliveryDate;
    @JsonProperty("DestRecieveDate")
    private String destRecieveDate;
    @JsonProperty("Destination")
    private String destination;
    @JsonProperty("DispatchCount")
    private int dispatchCount;
    @JsonProperty("Ewaybill")
    private List<Object> ewaybill;  // List of ewaybill entries
    @JsonProperty("ExpectedDeliveryDate")
    private String expectedDeliveryDate;
    @JsonProperty("Extras")
    private String extras;
    @JsonProperty("FirstAttemptDate")
    private String firstAttemptDate;
    @JsonProperty("InvoiceAmount")
    private int invoiceAmount;
    @JsonProperty("OrderType")
    private String orderType;
    @JsonProperty("Origin")
    private String origin;
    @JsonProperty("OriginRecieveDate")
    private String originRecieveDate;
    @JsonProperty("OutDestinationDate")
    private String outDestinationDate;
    @JsonProperty("PickUpDate")
    private String pickUpDate;
    @JsonProperty("PickedupDate")
    private String pickedupDate;
    @JsonProperty("PickupLocation")
    private String pickupLocation;
    @JsonProperty("PromisedDeliveryDate")
    private String promisedDeliveryDate;
    @JsonProperty("Quantity")
    private String quantity;
    @JsonProperty("RTOStartedDate")
    private String rtoStartedDate;
    @JsonProperty("ReferenceNo")
    private String referenceNo;
    @JsonProperty("ReturnPromisedDeliveryDate")
    private String returnPromisedDeliveryDate;
    @JsonProperty("ReturnedDate")
    private String returnedDate;
    @JsonProperty("ReverseInTransit")
    private boolean reverseInTransit;
    @JsonProperty("Scans")
    private List<Scan> scans;
    @JsonProperty("SenderName")
    private String senderName;
    @JsonProperty("Status")
    private Status status;

    // Getters and Setters
    // ... Getters and Setters for all fields
}
