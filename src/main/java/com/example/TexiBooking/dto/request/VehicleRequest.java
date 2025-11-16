package com.example.TexiBooking.dto.request;

import com.example.TexiBooking.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleRequest {
    private String model;
    private String licensePlate;
    private String color;
    private double perKmRate;
    // at starting vehicle is available that's why we will hard code it
    private VehicleType vehicleType;
    // Optionally, you can include a driver ID if you want to associate a driver
    // with the vehicle
    private Integer driverId; // Assuming you want to link the vehicle to a driver
}
