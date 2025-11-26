package com.example.TexiBooking.service;
import com.example.TexiBooking.dto.request.VehicleRequest;
import com.example.TexiBooking.dto.response.ApiResponse;
import com.example.TexiBooking.dto.response.VehicleResponse;
import com.example.TexiBooking.exception.ResourceNotFoundException;
import com.example.TexiBooking.model.Driver;
import com.example.TexiBooking.model.Vehicle;
import com.example.TexiBooking.repo.DriverRepo;
import com.example.TexiBooking.repo.VehicleRepo;
import com.example.TexiBooking.transformer.DriverTransformer;
import com.example.TexiBooking.transformer.VehicleTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private VehicleRepo vehicleRepo;

    // Register vehicle
    public VehicleResponse registerVehicle(VehicleRequest vehicleRequest) {

        Vehicle vehicle = VehicleTransformer.vehicleRequestToVehicle(vehicleRequest);

        // Always save vehicle
        Vehicle savedVehicle = vehicleRepo.save(vehicle);

        // If driverId is provided, link the vehicle to driver
        if (vehicleRequest.getDriverId() != null) {
            Driver driver = driverRepo.findById(vehicleRequest.getDriverId())
                    .orElseThrow(() -> new ResourceNotFoundException("Driver", "Id", vehicleRequest.getDriverId()));

            if (driver.getVehicle() != null) {
                throw new RuntimeException("Driver already has a registered vehicle");
            }

            driver.setVehicle(savedVehicle);
            driverRepo.save(driver);
        }

        // Prepare response
        VehicleResponse vehicleResponse = VehicleTransformer.vehicleToVehicleResponse(savedVehicle);
        if (vehicleRequest.getDriverId() != null) {
            Driver driver = driverRepo.findById(vehicleRequest.getDriverId()).get();
            vehicleResponse.setDriver(DriverTransformer.driverToDriverResponse(driver));
        }

        return vehicleResponse;
    }

    // Get vehicle by id
    public VehicleResponse getVehicleById(Integer vehicleId) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "Id", vehicleId));
        Driver driver = driverRepo.getDriverByVehicleId(vehicleId);
        return VehicleTransformer.vehicleToVehicleResponseWithDriver(vehicle, driver);
    }

    // Get all vehicles
    public List<VehicleResponse> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepo.findAll();
        List<VehicleResponse> responseList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Driver driver = driverRepo.getDriverByVehicleId(vehicle.getId());
            responseList.add(VehicleTransformer.vehicleToVehicleResponseWithDriver(vehicle, driver));
        }
        return responseList;
    }

    // Update vehicle
    public VehicleResponse updateVehicle(Integer vehicleId, VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "Id", vehicleId));

        vehicle.setModel(vehicleRequest.getModel());
        vehicle.setColor(vehicleRequest.getColor());
        vehicle.setPerKmRate(vehicleRequest.getPerKmRate());
        vehicle.setVehicleType(vehicleRequest.getVehicleType());

        Vehicle updatedVehicle = vehicleRepo.save(vehicle);
        Driver driver = driverRepo.getDriverByVehicleId(vehicleId);

        return VehicleTransformer.vehicleToVehicleResponseWithDriver(updatedVehicle, driver);
    }

    // Delete vehicle
    public ApiResponse deleteVehicleById(Integer vehicleId) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "Id", vehicleId));
        vehicleRepo.delete(vehicle);
        return new ApiResponse("Resource Deleted Successfully!", true,
                "Vehicle with ID " + vehicleId + " has been removed permanently.", null);
    }
}
