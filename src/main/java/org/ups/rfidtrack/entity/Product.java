package org.ups.rfidtrack.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "awb")
    private String AWB;
    String origin_data;
    String destination_data;
    String dimensions;
    String sender_data;
    String receiver_data;
    String sender_phone ;
    String receiver_phone;
    String product_details;
    String rfid;

    String status;
    String current_hub;

    double lat=0.0;
    double lon=0.0;

}
