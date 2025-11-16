package com.example.TexiBooking.dto.response;

import com.example.TexiBooking.model.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingResponse {
    private String pickUpLocation;
    private String dropOffLocation;
    private double tripDistanceInKm;
    private Date bookedAt;
    private Date lastUpdateAt;
    private Double billAmount;
    private TripStatus tripStatus;
    private double rating;
}
