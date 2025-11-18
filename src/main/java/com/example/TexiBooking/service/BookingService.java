package com.example.TexiBooking.service;

import com.example.TexiBooking.dto.request.BookingRequest;
import com.example.TexiBooking.dto.response.ApiResponse;
import com.example.TexiBooking.dto.response.BookingResponse;
import com.example.TexiBooking.dto.response.UpdateBookingResponse;
import com.example.TexiBooking.exception.ResourceNotFoundException;
import com.example.TexiBooking.exception.VehicleNotFoundException;
import com.example.TexiBooking.model.Booking;
import com.example.TexiBooking.model.Customer;
import com.example.TexiBooking.model.Driver;
import com.example.TexiBooking.model.Vehicle;
import com.example.TexiBooking.model.enums.TripStatus;
import com.example.TexiBooking.repo.BookingRepo;
import com.example.TexiBooking.repo.CustomerRepo;
import com.example.TexiBooking.repo.DriverRepo;
import com.example.TexiBooking.repo.VehicleRepo;
import com.example.TexiBooking.transformer.BookingTransformer;
import com.example.TexiBooking.transformer.UpBookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookingService {
    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    public BookingResponse bookRide(BookingRequest bookingRequest, Integer customerId) {
        //1. Find Customer by ID
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "ID", customerId));
        //2. Select available vehicle randomly
        Vehicle availableVehicle = vehicleRepo.getAvailableVehiclesRandomly();
        if (availableVehicle == null) {
            throw new VehicleNotFoundException("Sorry ! Vehicles Not Available");
        }
        //3. Convert BookingRequest → Booking entity(table)
        Booking booking = BookingTransformer.bookingRequestToBooking(bookingRequest, availableVehicle.getPerKmRate());
        //4. Save Booking in DB
        Booking savedBooking = bookingRepo.save(booking);
        //5. Set vehicle as NOT available
        availableVehicle.setAvailable(false);
        //6. Add booking to customer's booking list
        customer.getBookings().add(savedBooking);
        availableVehicle=vehicleRepo.save(availableVehicle);
        //7. Get Driver assigned to this vehicle
        Driver driver = driverRepo.getDriverByVehicleId(availableVehicle.getId());
        if (driver == null) {
            throw new RuntimeException("No driver Available for Booking..");
        }
        //8. Add booking to driver’s booking list
        driver.getBookings().add(savedBooking);
        //9. Save customer & driver changes
        customerRepo.save(customer);
        driverRepo.save(driver);
        //10. Send emails
        sendMail(customer, driver);
        sendMailToDriver(customer, driver, savedBooking);
        return BookingTransformer.bookingToBookingResponse(savedBooking, customer, availableVehicle, driver);
    }

    private void sendMail(Customer customer, Driver driver) {
        // Constructing the message text for the driver
        String text = "Congrats " + customer.getCustomerName() +
                "! Your " + driver.getVehicle().getVehicleType() +
                " has been booked successfully with " + driver.getName() + ".";

        String basicDetails = "For your convenience, here are some basic details of the driver and vehicle:\n" +
                "Name: " + driver.getName() + "\n" +
                "Phone No: " + driver.getPhone() + "\n" +
                "Vehicle Number: " + driver.getVehicle().getLicensePlate() + "\n" +
                "Vehicle Color: " + driver.getVehicle().getColor() + "\n" +
                "Vehicle Type: " + driver.getVehicle().getVehicleType() + ".";

        // Creating the email message
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("chaudharyvimal410@gmail.com");
        simpleMailMessage.setTo(customer.getEmailId());
        simpleMailMessage.setSubject("Cab Booked - Successfully");
        simpleMailMessage.setText(text + "\n" +
                basicDetails + "\n" +
                "Enjoy your journey!!\n" +
                "Best regards,\n" +
                "SwiftRide PVT LTD Technologies");

        // Sending the email
        javaMailSender.send(simpleMailMessage);

    }

    private void sendMailToDriver(Customer customer, Driver driver, Booking booking) {
        String text = "Congrats " + driver.getName() + ", Your vehicle " + driver.getVehicle().getVehicleType()
                + " has been booked successfully by " + customer.getCustomerName() + ".";

        String basicDetails = "For your convenience, here are some basic details of the customer and booking:\n" +
                "Customer Name: " + customer.getCustomerName() + "\n" +
                "Customer Phone No: " + customer.getCustomerName() + "\n" +
                "PickUpLocation: " + booking.getPickUpLocation() + "\n" +
                "DropOffLocation: " + booking.getDropOffLocation() + "\n" +
                "TripDistanceInKm: " + booking.getTripDistanceInKm() + "\n" +
                "Vehicle Type: " + driver.getVehicle().getVehicleType();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("chaudharyvimal410@gmail.com");
        simpleMailMessage.setTo(driver.getEmail()); // Send to driver's email
        simpleMailMessage.setSubject("Booking Confirmation - Successfully");
        simpleMailMessage.setText(text + "\n" + basicDetails + "\nEnjoy Your Journey !!"
                + "\nBest Regards,\nSwiftRide PVT LTD Technologies");

        javaMailSender.send(simpleMailMessage);
    }

    public void rateBooking(Integer bookingId, Double rating) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", bookingId));
        //if tripstatus is completed than set the rating
        if (TripStatus.COMPLETED.equals(booking.getTripStatus())) {
            booking.setRating(rating);
            bookingRepo.save(booking);
        } else {
            throw new RuntimeException("Cannot rate a booking that is not completed.");
        }
    }

    public BookingResponse getBooking(Integer bookingId, Integer customerId, Integer driverId) {
        //1. Fetch Customer and Driver from DB
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver", "Id", driverId));
        //2. Get the vehicle assigned to this driver
        Vehicle vehicle = driver.getVehicle();
        BookingResponse bookingResponse = null;
        boolean isBookingValidWithCustomer = false;
        //3. Check if booking belongs to driver
        boolean isBookingValidWithDriver = false;
        for (Booking booking : driver.getBookings()) {
            if (booking.getBookingId().equals(bookingId)) {
                bookingResponse = BookingTransformer.bookingToBookingResponse(booking, customer, vehicle, driver);
                isBookingValidWithDriver = true;
            }
        }
        if (!isBookingValidWithDriver) {
            throw new RuntimeException("Not An Authenticated Driver With Id : " + driverId);
        }
        //4. Check if booking belongs to customer
        for (Booking booking : customer.getBookings()) {
            if (booking.getBookingId().equals(bookingId)) {
                return BookingTransformer.bookingToBookingResponse(booking, customer, vehicle, driver);
            }
        }
        if (!isBookingValidWithCustomer) {
            throw new RuntimeException("Not An Authenticated Customer With Id : " + customerId);
        }
        //This method ensures(two for methods checks that):
        //Only the correct customer and correct driver can view a booking.
        //It protects booking data from:
        //Wrong driver
        //Wrong customer
        //Unauthorized access
        return bookingResponse;
    }

    //1. Validates booking exists
    //2. Validates customer exists
    //3. Validates the booking belongs to the customer
    //4. If yes → delete booking from the booking repo only, not from anywhere else
    //5. If no → return "not allowed" response
    public ApiResponse deleteBooking(Integer bookingId, Integer customerId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", bookingId));
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", customerId));
        for (Booking b : customer.getBookings()) {
            if (b.getBookingId().equals(bookingId)) {
                bookingRepo.delete(booking);
                return new ApiResponse("Resource Deleted Successfully !", true,
                        "Resource with ID " + bookingId + " has been removed permanently.", null);
            }
        }
        return new ApiResponse("User Not Allowed To Delete This Resource !", false,
                "Resource with ID " + customerId + " May Be Not Exist.", "400");
    }


    public List<BookingResponse> getBookingsByStatus(TripStatus status) {
        List<Booking> bookings = bookingRepo.findByTripStatus(status);
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (Booking b : bookings) {
            // getDriverByBooking also vehicle from driver
            Driver driver = driverRepo.findByBookings(b);
            if (driver == null) {
                throw new RuntimeException("Null Entry Is Present for Driver in Bookings !");
            }
            Vehicle vehicle = driver.getVehicle();
            // getCustomerByBooking
            Customer customer = customerRepo.findByBookings(b);
            if (customer == null) {
                throw new RuntimeException("Null Entry Is Present for Customer in Bookings");
            }
            bookingResponses.add(BookingTransformer.bookingToBookingResponse(b, customer, vehicle, driver));
            return bookingResponses;
        }
        return bookingResponses;
    }

    public Double findAverageRatingByDriverId(Integer driverId) {
        return bookingRepo.findAverageRatingByDriverId(driverId);
    }

    public UpdateBookingResponse updateBookingStatus(Integer driverId, Integer bookingId, TripStatus status) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", bookingId));

        //Only the driver assigned to this booking is allowed to update it.
        //This prevents random drivers from updating someone else’s ride.

        Driver driver = driverRepo.findByBookings(booking);
        if (driver == null) {
            throw new RuntimeException("UnAuthorized User!");
        }
        //update only when the driver is authorized.
        booking.setTripStatus(status);
        bookingRepo.save(booking);
        return UpBookTransformer.bookingToUpdateBookingResponse(booking);
    }

}
