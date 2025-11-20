package com.example.TexiBooking.controller;
import com.example.TexiBooking.dto.request.BookingRequest;
import com.example.TexiBooking.dto.response.BookingResponse;
import com.example.TexiBooking.service.BookingService;
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
    @PatchMapping("/{bookingId}/rate")
    public ResponseEntity<Void> rateBooking(@PathVariable Integer bookingId, @RequestParam Double rating) {
        try {
            bookingService.rateBooking(bookingId, rating);
            return ResponseEntity.ok().build(); // Return 200 OK on success
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request on error
        }
    }

}
