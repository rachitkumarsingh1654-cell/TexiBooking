package com.example.TexiBooking.service;
import com.example.TexiBooking.dto.response.BookingStatisticsResponse;
import com.example.TexiBooking.model.enums.TripStatus;
import com.example.TexiBooking.repo.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    @Autowired
    private BookingRepo bookingRepo;

    public BookingStatisticsResponse getBookingStatistics() {
        long totalBookings = bookingRepo.count();
        long completedBookings = bookingRepo.countByTripStatus(TripStatus.COMPLETED);
        long requestedBookings = bookingRepo.countByTripStatus(TripStatus.REQUESTED);

        long canceledBookings = bookingRepo.countByTripStatus(TripStatus.CANCELED);
        double totalRevenue = bookingRepo.calculateTotalRevenue(TripStatus.COMPLETED);
        return BookingStatisticsResponse.builder()
                .totalBookings(totalBookings)
                .completedBookings(completedBookings)
                .canceledBookings(canceledBookings)
                .pendingBookings(requestedBookings)
                .totalRevenue(totalRevenue)
                .build();
    }
}
