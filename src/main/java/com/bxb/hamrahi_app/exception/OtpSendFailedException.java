package com.bxb.hamrahi_app.exception;

/**
 * Exception thrown when sending OTP fails.
 */
public class OtpSendFailedException extends RuntimeException {
    /**
     * Constructs a new OtpSendFailedException with the specified detail message.
     *
     * @param message the detail message
     */
    public OtpSendFailedException(String message) {
        super(message);
    }
}
