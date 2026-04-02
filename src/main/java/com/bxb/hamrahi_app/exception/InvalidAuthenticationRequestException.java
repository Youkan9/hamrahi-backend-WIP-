package com.bxb.hamrahi_app.exception;

/**
 * Exception thrown when an authentication request is invalid,
 * such as missing required fields or invalid data formats.
 */
public class InvalidAuthenticationRequestException extends RuntimeException {
    public InvalidAuthenticationRequestException(String message) {
        super(message);
    }
}
