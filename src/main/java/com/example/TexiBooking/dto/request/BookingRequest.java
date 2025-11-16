package com.example.TexiBooking.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingRequest {
    private String pickUpLocation;
    private String dropOffLocation;
    private double tripDistanceInKm;
}
