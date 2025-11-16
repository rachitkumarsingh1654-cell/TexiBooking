package com.example.TexiBooking.dto.response;

import com.example.TexiBooking.model.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingResponse {
    private String pickUpLocation;
    private String dropOffLocation;
    private double tripDistanceInKm;
    private Date bookedAt;
    private Date lastUpdateAt;
    private Double billAmount;
    private TripStatus tripStatus;
    // linked things
    private CustomerResponse customer;
    private VehicleResponse vehicle;
}
