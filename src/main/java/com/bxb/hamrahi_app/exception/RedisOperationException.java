package com.bxb.hamrahi_app.exception;

/** * this exception is thrown when there is an error during Redis operations,
 *  such as
 * connection issues, timeouts, or data retrieval failures.
 */
public class RedisOperationException extends RuntimeException {
    /**
     * Constructs a new RedisOperationException with the
     * specified detail message.
     *
     * @param message the detail message explaining the
     *                reason for the exception
     */
    public RedisOperationException(String message) {
        super(message);
    }
}
