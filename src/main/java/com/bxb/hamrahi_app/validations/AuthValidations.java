package com.bxb.hamrahi_app.validations;

public interface AuthValidations {

    /**
     * Validates the refresh token for the given phone number and key.
     * @param refreshToken the refresh token to validate
     * @param userId the phone number associated with the refresh token
     * @param key the key used for validation
     */
    void validateRefreshToken(
            String refreshToken,
            String userId,
            String key);

    /**
     * Validates the provided OTP against the stored OTP.
     * @param storedOtp the OTP that was stored (e.g., in the database)
     * @param providedOtp the OTP provided by the user for validation
     */
    void validateOtp(String storedOtp,
                     String providedOtp, String phoneNumber);

    void validateOtpRequestLimit(
            String phoneNumber,
            int otpLimit,
            int windowMinutes,
            String rateKeyPrefix
    );
}
