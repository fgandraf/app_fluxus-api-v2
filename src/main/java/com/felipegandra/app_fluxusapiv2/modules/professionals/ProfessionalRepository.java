package com.felipegandra.app_fluxusapiv2.modules.professionals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> { }


