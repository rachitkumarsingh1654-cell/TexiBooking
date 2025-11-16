package com.example.TexiBooking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DriverPerformanceResponse {
    private String driverName;
    private long totalRides;
    private double averageRating;
}
