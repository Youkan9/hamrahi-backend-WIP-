package com.bxb.hamrahi_app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/** Response class for user information. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {
    /** message indicating the result of the operation. */
    private String message;

    /** The access token for the user. */
    private String accessToken;

    /** The refresh token for the user. */
    private String refreshToken;
}
