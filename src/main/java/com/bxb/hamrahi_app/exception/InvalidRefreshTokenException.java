package com.bxb.hamrahi_app.exception;

/** Custom exception thrown when an invalid refresh token is encountered. */
public class InvalidRefreshTokenException extends RuntimeException {
    /** Constructs a new InvalidRefreshTokenException with the specified
     * detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
