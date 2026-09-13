package com.evcharging.system.service;

public class InvalidBookingTimeException extends RuntimeException {

    public InvalidBookingTimeException(String message) {
        super(message);
    }
}