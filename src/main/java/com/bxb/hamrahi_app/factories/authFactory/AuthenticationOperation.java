package com.bxb.hamrahi_app.factories.authFactory;

import com.bxb.hamrahi_app.request.AuthenticationRequest;
import com.bxb.hamrahi_app.response.AuthenticationResponse;
import com.bxb.hamrahi_app.util.AuthenticationRequestType;

/**
 * Interface for authentication operations in the Hamrahi app.
 * This interface defines the contract for performing authentication-related
 * operations based on different types of authentication requests.
 */
public interface AuthenticationOperation {

    /**
     * Returns the type of authentication request that this operation handles.
     *
     * @return AuthenticationRequestType indicating the type of authentication
     * request.
     */
    AuthenticationRequestType getAuthenticationRequestType();

    /**
     * Performs the authentication operation based on the provided user request.
     *
     * @param request the UserRequest containing the details of the
     *                authentication request to be performed.
     * @return AuthenticationResponse containing the result of the
     * authentication operation.
     */
   AuthenticationResponse perform(AuthenticationRequest request);
}
