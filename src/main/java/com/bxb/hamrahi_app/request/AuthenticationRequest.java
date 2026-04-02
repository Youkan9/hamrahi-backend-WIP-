package com.bxb.hamrahi_app.request;

import com.bxb.hamrahi_app.util.AuthenticationRequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** * Request class for user-related operations.
 * This class encapsulates the data required for user registration or updates.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    /** The primary id of the user*/
    private Long userId;
    /** The phoneNumber of the user. */
    private String phoneNumber;
    /** The OTP (One-Time Password) for user verification. */
    private String otp;
    /** The type of authentication request (e.g., registration, login). */
    private AuthenticationRequestType authRequestType;
    /** The access token for the user. */
    private String RefreshToken;
}
