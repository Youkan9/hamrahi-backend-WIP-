package com.bxb.hamrahi_app.exception;

/**
 * Exception thrown when an invalid token type is encountered during authentication or authorization processes.
 */
public class InvalidTokenTypeException extends RuntimeException {
    /**
     * Constructs a new InvalidTokenTypeException with the specified detail
     * message.
     * @param message the detail message
     */
    public InvalidTokenTypeException(String message) {
        super(message);
    }
}
