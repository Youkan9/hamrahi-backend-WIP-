package com.bxb.hamrahi_app.repository;

import com.bxb.hamrahi_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** * Repository interface for User entity.
 * This interface extends JpaRepository to provide CRUD operations for
 * User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Method to find a user by their phone number.
     *
     * @param phoneNumber the phone number of the user to be found
     * @return an Optional containing the User if found, or empty if not
     * found
     */
    Optional<User> findByPhoneNumber(String phoneNumber);
}
