package com.ecommerce.magnik.repositories;

import com.ecommerce.magnik.model.AppRole;
import com.ecommerce.magnik.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}