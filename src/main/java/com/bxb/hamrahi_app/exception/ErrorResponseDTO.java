package com.bxb.hamrahi_app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for representing error responses in a
 * standardized format.
 * This class encapsulates details about an error that occurred during
 * API processing,
 * including the timestamp, HTTP status code, error type, message,
 * and the API path.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponseDTO {
    /** Timestamp when the error occurred. */
    private LocalDateTime localDateTime;

    /** HTTP status code of the error. */
    private Integer status;

    /** Short description of the error type. */
    private String error;

    /** Detailed message explaining the error. */
    private String message;

    /** The API path where the error occurred. */
    private String path;
}
