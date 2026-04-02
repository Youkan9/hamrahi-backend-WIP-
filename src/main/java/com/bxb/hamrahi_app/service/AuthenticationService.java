package com.bxb.hamrahi_app.service;

import com.bxb.hamrahi_app.request.AuthenticationRequest;
import com.bxb.hamrahi_app.response.AuthenticationResponse;

/** Service interface for handling user authentication operations. */
public interface AuthenticationService {

    /** Method to handle user authentication API requests.
     *
     * @param request the user request containing necessary data
     *                for authentication
     * @return AuthenticationResponse containing the result of the
     * authentication process
     */
    AuthenticationResponse authenticationApi(AuthenticationRequest request);
}