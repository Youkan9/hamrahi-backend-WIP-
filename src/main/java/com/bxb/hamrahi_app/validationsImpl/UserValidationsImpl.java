package com.bxb.hamrahi_app.validationsImpl;

import com.bxb.hamrahi_app.exception.InvalidAuthenticationRequestException;
import com.bxb.hamrahi_app.exception.UserNotFoundException;
import com.bxb.hamrahi_app.model.User;
import com.bxb.hamrahi_app.repository.UserRepository;
import com.bxb.hamrahi_app.validations.UserValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/** Implementation of user validation logic. */
@Component
@Log4j2
@RequiredArgsConstructor
public class UserValidationsImpl implements UserValidations {

    private final UserRepository userRepository;
    /** Country code to be prefixed to phone numbers if not already present. */
    private static final String COUNTRY_CODE = "+91";

    @Override
    public User validateAndGetUserById(Long userId) {
       return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User does not exist!"));
    }

    /** Validates and normalizes a phone number by ensuring it starts with
     * the country code +91. */
    @Override
    public String validateAndNormalizePhoneNumber(String phoneNumber) {

        if (phoneNumber == null || phoneNumber.isBlank()) {
            log.warn("Phone number is missing in request");
            throw new InvalidAuthenticationRequestException(
                    "Phone number is required");
        }

        phoneNumber = phoneNumber.trim();

        if (!phoneNumber.matches("^\\+?\\d+$")) {
            log.warn("Invalid phone number format: {}", phoneNumber);
            throw new InvalidAuthenticationRequestException(
                    "Phone number contains invalid characters");
        }

        if (!phoneNumber.startsWith(COUNTRY_CODE)) {

            log.info("Normalizing phone number {} by adding country code",
                    phoneNumber);

            phoneNumber = COUNTRY_CODE + phoneNumber;
        }

        String normalizedNumber = phoneNumber.replace(COUNTRY_CODE,
                "");

        if (normalizedNumber.length() != 10) {
            log.warn("Invalid phone number length: {}", phoneNumber);

            throw new InvalidAuthenticationRequestException(
                    "Phone number must contain 10 digits");
        }

        return COUNTRY_CODE + normalizedNumber;
    }
}
