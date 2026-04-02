package com.bxb.hamrahi_app.controller;

import com.bxb.hamrahi_app.request.AuthenticationRequest;
import com.bxb.hamrahi_app.response.AuthenticationResponse;
import com.bxb.hamrahi_app.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related API requests.
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    /**
     * Endpoint for user authentication.
     *
     * @param request the authentication request containing user credentials
     * @return the authentication response with success status and token
     * if applicable
     */
    @PostMapping("/authenticate")
    public AuthenticationResponse authenticateUser(
            @RequestBody final AuthenticationRequest request) {
        return authenticationService.authenticationApi(request);
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Service is up and running!");
    }

    @GetMapping("/police-dashboard")
    public String roleTest(){
        return "Role Officer authorized successfully!";
    }

    @GetMapping("/admin")
    public String adminTest(){
        return "Role admin authorized successfully!";
    }
}
