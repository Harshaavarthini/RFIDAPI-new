package org.ups.rfidtrack.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDtoForDesk {

    String origin_data;
    String destination_data;
    String dimensions;
    String sender_data;
    String receiver_data;
    String sender_phone ;
    String receiver_phone;
    String product_details;

}
