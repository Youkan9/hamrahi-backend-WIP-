package com.bxb.hamrahi_app.exception;

/**
 * this exception is thrown when the provided access token is invalid, expired,
 * or does not match the expected format.
 */
public class InvalidAccessTokenException extends RuntimeException {
    public InvalidAccessTokenException(String message) {
        super(message);
    }
}
