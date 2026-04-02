package com.bxb.hamrahi_app.util;

/** * Enum representing the types of authentication requests in the application.
 * This enum is used to differentiate between various authentication-related
 * operations, such as sending OTPs, verifying OTPs, refreshing tokens, and
 * logging out.
 */
public enum AuthenticationRequestType {
    /** Enum constant for sending an OTP (One-Time Password). */
    SEND_OTP,
    /** Enum constant for verifying an OTP (One-Time Password). */
    VERIFY_OTP,
    /** Enum constant for refreshing authentication tokens. */
    REFRESH_TOKEN,
    /** Enum constant for logging out a user. */
    LOGOUT
}
