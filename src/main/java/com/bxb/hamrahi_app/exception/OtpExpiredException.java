package com.bxb.hamrahi_app.exception;

/** * Exception thrown when an OTP (One-Time Password) has expired.
 */
public class OtpExpiredException extends RuntimeException {
    /**
     * Constructs a new OtpExpiredException with the specified detail message.
     *
     * @param message the detail message
     */
    public OtpExpiredException(String message) {
        super(message);
    }
}
