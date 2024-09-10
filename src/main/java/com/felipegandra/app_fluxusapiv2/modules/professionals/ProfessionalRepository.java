package com.felipegandra.app_fluxusapiv2.modules.professionals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    @Query(value = "SELECT PROFESSIONAL_ID, TAG, NAME, PROFESSION, PHONE1 FROM TBL_PROFESSIONAL ORDER BY TAG", nativeQuery = true)
    List<Object[]> findProfessionalIndex();


    //@Query(value = "SELECT PROFESSIONAL_ID, TAG, CONCAT(IFNULL(LEFT(PROFESSION, 3), ''), '. ', SUBSTRING_INDEX(NAME, ' ', 1), ' ', SUBSTRING_INDEX(SUBSTRING_INDEX(NAME, ' ', -1), ' ', 1)) AS Nameid FROM TBL_PROFESSIONAL ORDER BY TAG", nativeQuery = true)
    @Query(value = "SELECT p.PROFESSIONAL_ID, p.TAG, p.PROFESSION, p.NAME " +
            "FROM TBL_PROFESSIONAL p " +
            "ORDER BY p.TAG", nativeQuery = true)
    List<Object[]> findProfessionalTagNameId();

}