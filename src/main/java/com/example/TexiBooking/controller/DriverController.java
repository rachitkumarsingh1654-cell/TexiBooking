package com.example.TexiBooking.controller;

import com.example.TexiBooking.dto.request.DriverRequest;
import com.example.TexiBooking.dto.response.ApiResponse;
import com.example.TexiBooking.dto.response.DriverPerformanceResponse;
import com.example.TexiBooking.dto.response.DriverResponse;
import com.example.TexiBooking.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    DriverService driverService;

    @PostMapping
    public DriverResponse addDriver(@RequestBody DriverRequest driverRequest) {
        return driverService.addDriver(driverRequest);
    }

    @GetMapping("/{driverId}")
    public DriverResponse getDriverById(@PathVariable int driverId) {
        return driverService.getDriverById(driverId);
    }

    @GetMapping("/available")
    public List<DriverResponse> getAvailableDrivers() {
        return driverService.getAvailableDrivers();
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<DriverResponse> updateDriver(@PathVariable Integer driverId,
                                                       @RequestBody DriverRequest driverRequest) {
        DriverResponse response = driverService.updateDriver(driverId, driverRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<ApiResponse> delDriverById(@PathVariable Integer driverId) {
        ApiResponse response = driverService.delDriverById(driverId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    // get driver performance report
    @GetMapping("/{driverId}/report")
    public DriverPerformanceResponse getDriverPerformanceReport(@PathVariable Integer driverId) {
        return driverService.getDriverPerformanceReport(driverId);
    }
}
