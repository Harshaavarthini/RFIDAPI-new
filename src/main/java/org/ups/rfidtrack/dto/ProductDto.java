package org.ups.rfidtrack.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    String AWB;
    String origin_data;
    String destination_data;
    String dimensions;
    String sender_data;
    String receiver_data;
    String sender_phone ;
    String receiver_phone;
    String product_details;
    String rfid;

    @Override
    public String toString() {
        return "awb no : " + AWB + "\n" +
                "origin_data : " + origin_data + "\n" +
                "destination_data : " + destination_data + "\n" +
                "dimensions : " + dimensions + "\n" +
                "sender_data : " + sender_data + "\n" +
                "receiver_data : " + receiver_data + "\n" +
                "sender_phone : " + sender_phone + "\n" +
                "receiver_phone : " + receiver_phone + "\n" +
                "product_details : " + product_details;
    }
}

