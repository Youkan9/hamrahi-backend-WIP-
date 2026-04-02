package com.bxb.hamrahi_app.validations;

import com.bxb.hamrahi_app.model.User;

/** * Interface for user-related validations.
 * This interface defines methods for validating user input,
 * such as phone numbers.
 */
public interface UserValidations {

    User validateAndGetUserById(Long userId);

    /** Validates and normalizes a phone number.
     *
     * @param phoneNumber the phone number to validate and normalize
     * @return the normalized phone number if valid,
     * otherwise throws an exception
     */
    String validateAndNormalizePhoneNumber(String phoneNumber);
}
