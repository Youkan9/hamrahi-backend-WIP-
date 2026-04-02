package com.bxb.hamrahi_app.serviceImpl;

import com.bxb.hamrahi_app.factories.authFactory.AuthenticationFactory;
import com.bxb.hamrahi_app.factories.authFactory.AuthenticationOperation;
import com.bxb.hamrahi_app.request.AuthenticationRequest;
import com.bxb.hamrahi_app.response.AuthenticationResponse;
import com.bxb.hamrahi_app.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthenticationService interface for
 * handling user authentication operations.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {
    /**
     * Factory for creating authentication-related
     * services based on request types.
     */
    private final AuthenticationFactory authenticationFactory;

    /**
     * Handles authentication requests by delegating to the appropriate
     * authentication operation based on the request type.
     *
     * @param request the user request containing necessary information
     *                for authentication
     * @return an authentication response containing the result of the
     *         authentication operation
     */
    @Override
    public AuthenticationResponse authenticationApi(
            final AuthenticationRequest request) {
        log.info("Auth request type: {}", request.getAuthRequestType());
        AuthenticationOperation authOperation = authenticationFactory
                .getOperation(request.getAuthRequestType());
        return authOperation.perform(request);
    }
}
