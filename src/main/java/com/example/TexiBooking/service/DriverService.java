package com.example.TexiBooking.service;

import com.example.TexiBooking.dto.request.DriverRequest;
import com.example.TexiBooking.dto.response.ApiResponse;
import com.example.TexiBooking.dto.response.DriverPerformanceResponse;
import com.example.TexiBooking.dto.response.DriverResponse;
import com.example.TexiBooking.exception.ResourceNotFoundException;
import com.example.TexiBooking.model.Driver;
import com.example.TexiBooking.repo.BookingRepo;
import com.example.TexiBooking.repo.DriverRepo;
import com.example.TexiBooking.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {
    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private BookingRepo bookingRepo;

    public DriverResponse addDriver(DriverRequest driverRequest) {
        Driver driver = DriverTransformer.requestDriverToDriver(driverRequest);
        Driver saved = driverRepo.save(driver);
        return DriverTransformer.driverToDriverResponse(saved);
    }

    public DriverResponse getDriverById(int driverId) {
        Driver driver = driverRepo.findById(driverId).orElse(null);
        if (driver == null)
            return null;
        return DriverTransformer.driverToDriverResponse(driver);
    }

    public List<DriverResponse> getAvailableDrivers() {
        List<Driver> availableDrivers = driverRepo.findByVehicleIsAvailable(true);
        return availableDrivers.stream().map(driver -> DriverTransformer.driverToDriverResponse(driver))
                .collect(Collectors.toList());
    }

    public ApiResponse delDriverById(Integer driverId) {
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "Id", driverId));
        driverRepo.delete(driver);
        return new ApiResponse("Resource Deleted Successfully !", true,
                "Resource with ID " + driverId + " has been removed permanently.", null);
    }

    public DriverResponse updateDriver(Integer driverId, DriverRequest driverRequest) {
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "Id", driverId));
        if (driver != null) {
            Driver newDriver = DriverTransformer.requestDriverToDriver(driverRequest);
            return DriverTransformer.driverToDriverResponse(newDriver);
        }
        return null;
    }

    // driver report
    public DriverPerformanceResponse getDriverPerformanceReport(Integer driverId) {
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "Id", driverId));
        long totalRides = bookingRepo.countCompletedBookingsByDriverId(driverId);
        double avgRating = bookingRepo.findAverageRatingByDriverId(driverId);
        return DriverPerformanceResponse.builder()
                .driverName(driver.getName())
                .totalRides(totalRides)
                .averageRating(avgRating)
                .build();
    }

}
