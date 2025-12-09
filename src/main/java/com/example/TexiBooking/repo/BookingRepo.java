package com.example.TexiBooking.repo;
import com.example.TexiBooking.model.Booking;
import com.example.TexiBooking.model.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer>{

    @Query(value = "SELECT COALESCE(AVG(rating), 0.0) FROM bookings WHERE trip_status = 'COMPLETED' AND driver_id = :driverId", nativeQuery = true)
    double findAverageRatingByDriverId(@Param("driverId") Integer driverId);

    @Query(value = "SELECT COUNT(*) FROM bookings WHERE trip_status = 'COMPLETED' AND driver_id = :driverId", nativeQuery = true)
    long countCompletedBookingsByDriverId(@Param("driverId") Integer driverId);

    long countByTripStatus(TripStatus status);

    @Query("SELECT COALESCE(SUM(b.billAmount), 0) FROM Booking b WHERE b.tripStatus = :tripStatus")
    double calculateTotalRevenue(TripStatus tripStatus);

    List<Booking> findByTripStatus(TripStatus status);

}
