package com.example.TexiBooking.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingStaticsResponse {
    private long totalBookings;
    private long completedBookings;
    private long canceledBookings;
    private long pendingBookings;
    private double totalRevenue;
}
