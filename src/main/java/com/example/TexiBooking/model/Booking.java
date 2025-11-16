package com.example.TexiBooking.model;

import com.example.TexiBooking.model.enums.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "bookings")
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;
    private String pickUpLocation;
    private String dropOffLocation;
    private double tripDistanceInKm;
    @CreationTimestamp
    private Date bookedAt;
    @UpdateTimestamp
    private Date lastUpdateAt;
    private Double billAmount;
    private Double rating;
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;

}
