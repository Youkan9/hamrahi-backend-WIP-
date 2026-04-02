package com.bxb.hamrahi_app.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * GlobalExceptionHandler is a centralized exception handler
 * for the application.
 * It captures and handles various exceptions that may occur during
 * request processing,
 * providing consistent error responses to the client.
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private ErrorResponseDTO handleInvalidRefreshTokenException(
            final InvalidRefreshTokenException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(value = InvalidAccessTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private ErrorResponseDTO handleInvalidAccessTokenException(
            final InvalidAccessTokenException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(value = InvalidAuthenticationRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResponseDTO handleInvalidAuthRequestException(
            final InvalidAuthenticationRequestException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(value = InvalidOtpException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private ErrorResponseDTO handleInvalidOtpException(
            final InvalidOtpException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(value = InvalidTokenTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorResponseDTO handleInvalidTokenTypeException(
            final InvalidTokenTypeException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(value = OtpExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private ErrorResponseDTO handleOtpExpiredException(
            final OtpExpiredException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(value = OtpSendFailedException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    private ErrorResponseDTO handleOtpSendFailedException(
            final OtpSendFailedException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.BAD_GATEWAY.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(value = RedisOperationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorResponseDTO handleRedisOperationExceptionException(
            final RedisOperationException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ErrorResponseDTO handleUserNotFoundException(
            final UserNotFoundException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ErrorResponseDTO handleUserAlreadyExistException(
            final UserAlreadyExistsException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorResponseDTO handleRuntimeException(
            final RuntimeException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
               e.getMessage(),
                request.getRequestURI()
        );
    }
    @ExceptionHandler(OtpBlockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    private ErrorResponseDTO handleOtpBlockedException(
            final OtpBlockedException e,
            final HttpServletRequest request) {
        return new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
    }
}
