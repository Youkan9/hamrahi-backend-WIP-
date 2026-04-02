package com.bxb.hamrahi_app.exception;
/**
 * Exception thrown when a user has exceeded the allowed number of OTP requests
 * within a specified time window, resulting in a temporary block on OTP requests.
 */
public class OtpBlockedException extends RuntimeException {
   /**
     * Constructs a new OtpBlockedException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the block
     */
    public OtpBlockedException(String message) {
        super(message);
    }
}
