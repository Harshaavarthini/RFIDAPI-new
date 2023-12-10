package org.ups.rfidtrack.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductDtoForCust {

    String origin_data;
    String destination_data;
    String dimensions;
    String sender_data;
    String receiver_data;
    String sender_phone ;
    String receiver_phone;
    String product_details;


    public ProductDtoForCust(String origin_data, String destination_data, String dimensions, String sender_data, String receiver_data, String sender_phone, String receiver_phone, String product_details) {
        this.origin_data = origin_data;
        this.destination_data = destination_data;
        this.dimensions = dimensions;
        this.sender_data = sender_data;
        this.receiver_data = receiver_data;
        this.sender_phone = sender_phone;
        this.receiver_phone = receiver_phone;
        this.product_details = product_details;
    }

    public String getOrigin_data() {
        return origin_data;
    }

    public String getDestination_data() {
        return destination_data;
    }

    public String getDimensions() {
        return dimensions;
    }

    public String getSender_data() {
        return sender_data;
    }

    public String getReceiver_data() {
        return receiver_data;
    }

    public String getSender_phone() {
        return sender_phone;
    }

    public String getReceiver_phone() {
        return receiver_phone;
    }

    public String getProduct_details() {
        return product_details;
    }

    @Override
    public String toString() {
        return "origin_data : " + origin_data + "\n" +
                "destination_data : " + destination_data + "\n" +
                "dimensions : " + dimensions + "\n" +
                "sender_data : " + sender_data + "\n" +
                "receiver_data : " + receiver_data + "\n" +
                "sender_phone : " + sender_phone + "\n" +
                "receiver_phone : " + receiver_phone + "\n" +
                "product_details : " + product_details;
    }
}
