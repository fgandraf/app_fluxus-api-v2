package com.felipegandra.app_fluxusapiv2.modules.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query(value = "SELECT CNPJ, COMPANY_NAME, CONTRACT_NOTICE, CONTRACT_NUMBER FROM TBL_PROFILE WHERE PROFILE_ID = 1", nativeQuery = true)
    List<Object[]> findToPrint();


    @Query(value = "SELECT TRADING_NAME FROM TBL_PROFILE WHERE PROFILE_ID = 1", nativeQuery = true)
    Optional<String> findTradingName();
}
