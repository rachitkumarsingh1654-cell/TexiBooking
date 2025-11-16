package com.example.TexiBooking.transformer;

import com.example.TexiBooking.dto.request.VehicleRequest;
import com.example.TexiBooking.dto.response.VehicleResponse;
import com.example.TexiBooking.model.Driver;
import com.example.TexiBooking.model.Vehicle;

public class VehicleTransformer {
    public static Vehicle vehicleRequestToVehicle(VehicleRequest vehicleRequest) {
        return Vehicle.builder()
                .vehicleType(vehicleRequest.getVehicleType())
                .color(vehicleRequest.getColor())
                .licensePlate(vehicleRequest.getLicensePlate().toUpperCase())
                .model(vehicleRequest.getModel())
                .perKmRate(vehicleRequest.getPerKmRate())
                .isAvailable(true).build();
    }

    public static VehicleResponse vehicleToVehicleResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .vehicleType(vehicle.getVehicleType())
                .licensePlate(vehicle.getLicensePlate())
                .model(vehicle.getModel())
                .perKmRate(vehicle.getPerKmRate())
                .isAvailable(vehicle.isAvailable())
                .build();
    }

    public static VehicleResponse vehicleToVehicleResponseWithDriver(Vehicle vehicle, Driver driver) {
        VehicleResponse vehicleResponse = vehicleToVehicleResponse(vehicle);
        vehicleResponse.setDriver(DriverTransformer.driverToDriverResponse(driver));
        return vehicleResponse;
    }
}
