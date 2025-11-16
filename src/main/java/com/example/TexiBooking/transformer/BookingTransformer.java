package com.example.TexiBooking.transformer;

import com.example.TexiBooking.dto.request.BookingRequest;
import com.example.TexiBooking.dto.response.BookingResponse;
import com.example.TexiBooking.dto.response.VehicleResponse;
import com.example.TexiBooking.model.Booking;
import com.example.TexiBooking.model.Customer;
import com.example.TexiBooking.model.Driver;
import com.example.TexiBooking.model.Vehicle;
import com.example.TexiBooking.model.enums.TripStatus;

public class BookingTransformer {
    public static Booking bookingRequestToBooking(BookingRequest bookingRequest, double perKmCharge) {
        return Booking.builder()
                .pickUpLocation(bookingRequest.getPickUpLocation())
                .dropOffLocation(bookingRequest.getDropOffLocation())
                .tripDistanceInKm(bookingRequest.getTripDistanceInKm())
                .tripStatus(TripStatus.REQUESTED)
                .rating(0.0)
                .billAmount(bookingRequest.getTripDistanceInKm() * perKmCharge).build();
    }

    public static BookingResponse bookingToBookingResponse(Booking booking, Customer customer, Vehicle vehicle,
                                                           Driver driver) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setDriver(DriverTransformer.driverToDriverResponse(driver));
        return BookingResponse.builder()
                .pickUpLocation(booking.getPickUpLocation())
                .dropOffLocation(booking.getDropOffLocation())
                .billAmount(booking.getBillAmount())
                .tripDistanceInKm(booking.getTripDistanceInKm())
                .tripStatus(booking.getTripStatus())
                .bookedAt(booking.getBookedAt())
                .lastUpdateAt(booking.getLastUpdateAt())
                .customer(CustomerTransformer.customerToCustomerResponse(customer))
                .vehicle(VehicleTransformer.vehicleToVehicleResponseWithDriver(vehicle, driver))
                .build();
    }
}
