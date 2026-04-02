package com.bxb.hamrahi_app.factories.authFactory;

import com.bxb.hamrahi_app.exception.InvalidRefreshTokenException;
import com.bxb.hamrahi_app.exception.InvalidTokenTypeException;
import com.bxb.hamrahi_app.jwt.JwtUtil;
import com.bxb.hamrahi_app.model.User;
import com.bxb.hamrahi_app.request.AuthenticationRequest;
import com.bxb.hamrahi_app.response.AuthenticationResponse;
import com.bxb.hamrahi_app.util.AuthenticationRequestType;
import com.bxb.hamrahi_app.validations.AuthValidations;
import com.bxb.hamrahi_app.validations.UserValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Service implementation for handling refresh token
 * authentication operations.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class RefreshTokenService implements AuthenticationOperation {


    private static final String REFRESH_TOKEN_KEY = "refresh:";

    /**
     * Utility for handling JWT operations, such as token generation
     * and validation, which is essential for the refresh token process.
     */
    private final JwtUtil jwtUtil;

    /**
     * Redis template for accessing and managing refresh tokens stored
     * in Redis, allowing for efficient retrieval and storage of tokens
     * with expiration.
     */
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Validation component for authentication-related operations,
     * ensuring that the refresh token and associated data meet the required
     * criteria before proceeding with the token refresh process.
     */
    private final AuthValidations authValidations;

    private final UserValidations userValidations;

    /**
     * Expiration time for refresh tokens,
     * injected from application properties.
     */
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    /**
     * Returns the type of authentication request this service handles,
     * which is REFRESH_TOKEN.
     */
    @Override
    public AuthenticationRequestType getAuthenticationRequestType() {
        return AuthenticationRequestType.REFRESH_TOKEN;
    }

    /**
     * Performs the refresh token authentication operation based
     * on the provided user request.
     *
     * @param request the user request containing necessary information
     *                for the refresh token operation
     * @return an authentication response containing the result
     * of the refresh token operation
     */
    @Override
    public AuthenticationResponse perform(
            final AuthenticationRequest request) {
        try {
            String refreshToken = request.getRefreshToken();
            String userId = jwtUtil.extractUserId(refreshToken);

            User user = userValidations.validateAndGetUserById(
                    Long.parseLong(userId));

            String key = buildRefreshTokenRedisKey(userId);

            authValidations.validateRefreshToken(refreshToken, userId, key);

            String newAccessToken = jwtUtil.generateAccessToken(user);

            String newRefreshToken = jwtUtil.generateRefreshToken(user);

            redisTemplate.opsForValue().set(key, newRefreshToken,
                    Duration.ofMillis(refreshTokenExpiration));

            log.info("Access token refreshed for user {}", userId);

            return AuthenticationResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .message("Access token refreshed successfully")
                    .build();

        }  catch (InvalidRefreshTokenException | InvalidTokenTypeException e) {

            log.warn("Refresh token validation failed: {}", e.getMessage());
            throw e;

        } catch (Exception e) {

            log.error("Unexpected error during refresh token process", e);

            throw e;
        }
    }

    /**
     * Helper method to construct the Redis key for storing and
     * retrieving refresh tokens based on the user's phone number.
     *
     * @param userId the user's phone number used to create the Redis key
     * @return the constructed Redis key for the refresh token
     */
    private String buildRefreshTokenRedisKey(String userId) {
        return REFRESH_TOKEN_KEY + userId;
    }

}