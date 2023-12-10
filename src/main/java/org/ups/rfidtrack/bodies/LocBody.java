package org.ups.rfidtrack.bodies;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocBody {
    double lat;
    double lon;
}
