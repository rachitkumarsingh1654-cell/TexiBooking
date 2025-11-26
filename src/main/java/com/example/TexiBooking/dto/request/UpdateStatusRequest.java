package com.example.TexiBooking.dto.request;

import com.example.TexiBooking.model.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusRequest {
    private Integer driverId; // remove if using JWT auth
    private TripStatus status;
}
