package com.felipegandra.app_fluxusapiv2.modules.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);

    UserDetails findByProfessionalId(Long professionalId);


    Optional<User> findById(Long id);

}