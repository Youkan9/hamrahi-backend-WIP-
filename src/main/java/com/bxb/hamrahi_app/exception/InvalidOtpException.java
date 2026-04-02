package com.bxb.hamrahi_app.exception;

/**
 * Exception thrown when an invalid OTP is provided during verification.
 */
public class InvalidOtpException extends RuntimeException {
  /**
     * Constructs a new InvalidOtpException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidOtpException(String message) {
        super(message);
    }
}
