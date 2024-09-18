package com.felipegandra.app_fluxusapiv2.modules.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

    Optional<User> getByEmail(String email);

    Optional<User> findByProfessionalId(Long professionalId);
}