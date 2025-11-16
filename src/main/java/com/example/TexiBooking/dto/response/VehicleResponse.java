package com.example.TexiBooking.dto.response;

import com.example.TexiBooking.model.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VehicleResponse {
    private String model;
    private String licensePlate;
    private String color;
    private double perKmRate;
    private VehicleType vehicleType;
    private boolean isAvailable;
    private DriverResponse driver;
}
