package org.ups.rfidtrack.entity;

import      jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


@Entity
@Table(name = "users")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "usermail")
    String userEmail;

    @Column(name = "userpwd")
    String userPassword;

    String role;


}
