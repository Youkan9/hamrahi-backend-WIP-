package com.bxb.hamrahi_app.validationsImpl;

import com.bxb.hamrahi_app.exception.*;
import com.bxb.hamrahi_app.jwt.JwtUtil;
import com.bxb.hamrahi_app.validations.AuthValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Implementation of authentication validation logic,
 * specifically for validating
 * refresh tokens. This class interacts with Redis to check the validity of
 * refresh tokens and uses JWT utilities to ensure the token's integrity. */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthValidationsImpl implements AuthValidations {

    /** * Redis template for interacting with Redis datastore,
     *  used for storing and validating refresh tokens. */
    private final RedisTemplate<String, String> redisTemplate;

    /** Utility for handling JWT operations, such as token validation,
     * which is essential for validating refresh tokens. */
    private final JwtUtil jwtUtil;

    /** Redis key prefix for storing OTP failure counts, used to track
     * failed OTP validation attempts and potentially block users after
     * exceeding a certain threshold. */
    private static final String failKey = "otp:fail:";

    /** Redis key prefix for blocking users after exceeding OTP failure limits,
     * used to prevent further OTP validation attempts for a certain period. */
    private static final String blockKey = "otp:block:";

    /** Validates the provided refresh token by checking its presence in Redis
     * and ensuring it is valid according to JWT standards.
     * @param refreshToken the refresh token to validate
     * @param userId the phone number associated with the refresh token
     * @param key the Redis key used to store the refresh token for the user */
    @Override
    public void validateRefreshToken(
            final String refreshToken,
            final String userId,
            final String key) {

        String tokenType = jwtUtil.extractTokenType(refreshToken);

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new InvalidRefreshTokenException(
                    "Refresh token is missing from request");
        }

        String storedRefreshToken = redisTemplate.opsForValue().get(key);

        if (storedRefreshToken == null) {
            throw new InvalidRefreshTokenException(
                    "No active refresh token found for this user");
        }

        if (!"REFRESH".equals(tokenType)) {
            throw new InvalidTokenTypeException(
                    "Token type mismatch: expected REFRESH token");
        }

        if (!refreshToken.equals(storedRefreshToken)) {
            throw new InvalidRefreshTokenException(
                    "Provided refresh token does not match stored token");
        }

        if (!jwtUtil.isTokenValid(refreshToken, userId)) {
            throw new InvalidRefreshTokenException(
                    "Refresh token is expired or invalid");
        }

    }

    /** Validates the provided OTP by comparing it with the stored OTP in Redis.
     * It checks for the presence of the stored OTP, ensures the provided OTP
     * is not null or blank, and verifies that both OTPs match. If any of these
     * conditions fail, appropriate exceptions are thrown to indicate the error.
     */
    @Override
    public void validateOtp(String storedOtp, String providedOtp,
                            String phoneNumber) {

        if(redisTemplate.hasKey(blockKey + phoneNumber)){
  log.warn("Phone number {} is currently blocked due to multiple failed OTP attempts",
                    phoneNumber);
            throw new OtpBlockedException(
                    "Too many failed OTP attempts. Please try again later.");
        }

        if (storedOtp == null) {
            log.warn("OTP expired or not found in Redis");
            throw new OtpExpiredException(
                    "OTP has expired or is not available");
        }

        if (providedOtp == null || providedOtp.isBlank()) {
            log.warn("OTP not provided in request");
            throw new InvalidOtpException("OTP must be provided");
        }


        if (!storedOtp.equals(providedOtp)) {

            Long failCount = redisTemplate.opsForValue().
                    increment(failKey + phoneNumber);

            redisTemplate.expire(failKey + phoneNumber,
                    Duration.ofMinutes(30));
            log.warn("Invalid OTP attempt");
            if (failCount!= null && failCount >= 5) {
                redisTemplate.opsForValue().set(blockKey + phoneNumber,
                        "BLOCKED",
                        Duration.ofMinutes(30));
         log.warn("Phone number {} blocked due to multiple failed OTP attempts",
                        phoneNumber);
            }
            throw new InvalidOtpException("Invalid OTP provided");
        }
        redisTemplate.delete(failKey);

        log.info("OTP validated successfully");
    }

    /**
     * Validates the OTP request rate for a given phone number.
     *
     * <p>This method ensures that a user cannot request more than a specified
     * number of OTPs within a defined time window. The rate limiting is
     * implemented using Redis where each phone number has a counter key.</p>
     *
     * <p>Behavior:</p>
     * <ul>
     *     <li>If no OTP request exists for the phone number,
     *     a new counter is
     *     created with value {@code 1} and a TTL equal to the
     *     defined time window.</li>
     *     <li>If the counter exists and is below the allowed limit,
     *     it is incremented.</li>
     *     <li>If the counter reaches the maximum allowed limit,
     *     the request is rejected.</li>
     * </ul>
     *
     * <p>The first OTP request starts the rate-limit window.</p>
     *
     * @param phoneNumber   normalized phone number of the user requesting OTP
     * @param otpLimit      maximum number of OTP requests allowed
     *                     in the window
     * @param windowMinutes time window duration in minutes
     * @param rateKeyPrefix Redis key prefix used for storing OTP
     *                     rate limit data
     *
     * @throws InvalidAuthenticationRequestException
     *         if the user exceeds the allowed OTP request limit
     */
    @Override
    public void validateOtpRequestLimit(
            String phoneNumber,
            int otpLimit,
            int windowMinutes,
            String rateKeyPrefix
    ) {
        log.info("Checking OTP rate limit for phone number: {}",
                phoneNumber);
        String redisKey = rateKeyPrefix + phoneNumber;

        String countStr = redisTemplate.opsForValue().get(redisKey);

        int count = countStr == null ? 0 : Integer.parseInt(countStr);
        log.info("Current OTP request count for {} is {}",
                phoneNumber,
                countStr);

        if (count >= otpLimit) {
            log.error("OTP rate limit exceeded for phone number: {}",
                    phoneNumber);
            throw new InvalidAuthenticationRequestException(
                    "Maximum OTP requests reached. Please try again later."
            );
        }

        if (count == 0) {

            redisTemplate.opsForValue().set(
                    redisKey,
                    "1",
                    Duration.ofMinutes(windowMinutes)
            );

        } else {
            redisTemplate.opsForValue().increment(redisKey);
            log.info("Incremented OTP request count for {}. Current count: {}",
                    phoneNumber,
                    count);
        }
    }
}
