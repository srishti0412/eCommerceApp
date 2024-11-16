package com.ecommerce.magnik.repositories;
import com.ecommerce.magnik.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Use 'email' if that is the correct field for login
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}

