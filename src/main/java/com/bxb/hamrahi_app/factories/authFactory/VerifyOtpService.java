package com.bxb.hamrahi_app.factories.authFactory;

import com.bxb.hamrahi_app.exception.InvalidOtpException;
import com.bxb.hamrahi_app.exception.OtpExpiredException;
import com.bxb.hamrahi_app.jwt.JwtUtil;
import com.bxb.hamrahi_app.model.User;
import com.bxb.hamrahi_app.repository.UserRepository;
import com.bxb.hamrahi_app.request.AuthenticationRequest;
import com.bxb.hamrahi_app.response.AuthenticationResponse;
import com.bxb.hamrahi_app.util.AuthenticationRequestType;
import com.bxb.hamrahi_app.util.UserRole;
import com.bxb.hamrahi_app.validations.AuthValidations;
import com.bxb.hamrahi_app.validations.UserValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Log4j2
@RequiredArgsConstructor
public class VerifyOtpService implements AuthenticationOperation{


    private static final String OTP_KEY = "otp:";

    private static final String REFRESH_TOKEN_KEY = "refresh:";

    /**
     * Repository for accessing user data.
     */
    private final UserRepository userRepository;

    /**
     * Validation component for user-related operations.
     */
    private final UserValidations userValidations;

    /**
     * Redis template for storing OTPs with expiration.
     */
    private final RedisTemplate<String, String> redisTemplate;

    /** Validation component for authentication-related operations. */
    private final AuthValidations authValidations;

    /** Utility for generating JWT tokens for authenticated users. */
    private final JwtUtil jwtUtil;

    /** Expiration time for refresh tokens,
     * injected from application properties. */
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    public AuthenticationRequestType getAuthenticationRequestType() {
        return AuthenticationRequestType.VERIFY_OTP;
    }

    @Override
    public AuthenticationResponse perform(
            final AuthenticationRequest request) {
        try {
            log.info(
             "Received OTP verification request for phone number: {}",
                    request.getPhoneNumber());

            String phoneNumber = userValidations
                    .validateAndNormalizePhoneNumber(request.getPhoneNumber());

            String otpRedisKey = OTP_KEY + phoneNumber;

        String storedOtp = redisTemplate.opsForValue().get(otpRedisKey);

            authValidations.validateOtp(storedOtp, request.getOtp(), phoneNumber);

            redisTemplate.delete(otpRedisKey);

            log.info(
                    "OTP verified successfully for phone number: {}",
                    phoneNumber);

            User user = userRepository.findByPhoneNumber(phoneNumber)
                    .orElseGet(() -> {

                        log.info("User not found. Creating new user.");

                        User newUser = User.builder()
                                .phoneNumber(phoneNumber)
                                .userRole(UserRole.CITIZEN)
                                .build();

                        return userRepository.save(newUser);
                    });

            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            String refreshTokenKey = REFRESH_TOKEN_KEY + user.getUserId();

            redisTemplate.opsForValue().set(refreshTokenKey,
                    refreshToken,
                    Duration.ofMillis(refreshTokenExpiration));

            return AuthenticationResponse.builder()
                    .message("OTP verified successfully")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (OtpExpiredException | InvalidOtpException e) {

            log.warn("OTP verification failed: {}", e.getMessage());
            throw e;

        } catch (RuntimeException e) {

            log.error("Unexpected error during OTP verification for {}",
                    request.getPhoneNumber(), e);

            throw e;
        }
    }
}
