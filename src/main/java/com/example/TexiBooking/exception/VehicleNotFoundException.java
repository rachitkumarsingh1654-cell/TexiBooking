package com.example.TexiBooking.exception;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(String message) {
        super(message);
        System.err.println("\u001B[31mError: Resource not found\u001B[0m"); // red Colored error on console
    }
}
