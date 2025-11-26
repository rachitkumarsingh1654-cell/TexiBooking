package com.example.TexiBooking.controller;
import com.example.TexiBooking.dto.request.BookingRequest;
import com.example.TexiBooking.dto.request.UpdateStatusRequest;
import com.example.TexiBooking.dto.response.ApiResponse;
import com.example.TexiBooking.dto.response.BookingResponse;
import com.example.TexiBooking.dto.response.BookingStatisticsResponse;
import com.example.TexiBooking.dto.response.UpdateBookingResponse;
import com.example.TexiBooking.model.enums.TripStatus;
import com.example.TexiBooking.service.BookingService;
import com.example.TexiBooking.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//This class receives HTTP requests and returns HTTP responses.
@RestController
//All APIs inside this class will start with /booking
@RequestMapping("/booking")
public class BookingController {
    //call bookingService to handle the business logic
    @Autowired
    private BookingService bookingService;

    @Autowired
    private StatisticsService statisticsService;


    // which customer is booking
    //HTTP Method → POST
    //URL → /booking/customer/{customerId}
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<BookingResponse> bookRide(@RequestBody BookingRequest bookingRequest,
                                                    @PathVariable Integer customerId) {
        try {
            BookingResponse bookingResponse = bookingService.bookRide(bookingRequest, customerId);
            return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    // rate booking
    //PATCH /booking/{bookingId}/rate?rating=4.5
    //It updates only one field (rating), so PATCH is the correct HTTP verb.
    //set the rating
    @PatchMapping("/{bookingId}/rate")
    public ResponseEntity<Void> rateBooking(@PathVariable Integer bookingId, @RequestParam Double rating) {
        try {
            bookingService.rateBooking(bookingId, rating);
            return ResponseEntity.ok().build(); // Return 200 OK on success
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request on error
        }
    }

    //GET /bookings/{bookingId}?customerId=123&driverId=456
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Integer bookingId, @RequestParam Integer customerId,
                                                      @RequestParam Integer driverId) {
        BookingResponse response = bookingService.getBooking(bookingId, customerId, driverId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // driver can update tip status like cancel orr completed
    @PatchMapping("/{bookingId}/status")
    public ResponseEntity<UpdateBookingResponse> updateBookingStatus(
            @PathVariable Integer bookingId,@RequestBody UpdateStatusRequest request) {
        Integer driverId = request.getDriverId();
        TripStatus status = request.getStatus();
        UpdateBookingResponse updatedBooking = bookingService.updateBookingStatus(driverId, bookingId, status);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    ///bookings/15?customerId=5
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse> deleteBooking(@PathVariable Integer bookingId,
                                                     @RequestParam Integer customerId) {
        ApiResponse response = bookingService.deleteBooking(bookingId, customerId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED); // Return 204 No Content on success
    }


    ///booking/status?status=COMPLETED
    @GetMapping("/status")
    public ResponseEntity<List<BookingResponse>> getBookingsByStatus(@RequestParam TripStatus status) {
        List<BookingResponse> responseList = bookingService.getBookingsByStatus(status);
        if (responseList.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no bookings found
        }
        return new ResponseEntity<>(responseList, HttpStatus.ACCEPTED); // Return 200 OK with the list of bookings
    }

    @GetMapping("/average-rating/{driverId}")
    public ResponseEntity<Double> findAverageRatingByDriverId(@PathVariable Integer driverId) {
        Double averageRating = bookingService.findAverageRatingByDriverId(driverId);
        if (averageRating != null) {
            return ResponseEntity.ok(averageRating); // Return 200 OK with the average rating
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if no completed bookings found
        }
    }

    // statistics for booking
    @GetMapping("/statistics")
    public ResponseEntity<BookingStatisticsResponse> getBookingStatistics() {
        BookingStatisticsResponse bookingStatistics = statisticsService.getBookingStatistics();
        return new ResponseEntity<>(bookingStatistics, HttpStatus.OK);
    }


}
