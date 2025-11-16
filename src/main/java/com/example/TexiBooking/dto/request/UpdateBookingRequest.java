package com.example.TexiBooking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingRequest {
    private Integer bookingId;
    private Integer customerId;
}
