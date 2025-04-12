package com.felipegandra.app_fluxusapiv2.modules.branches;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {

    @Query(value = "SELECT BRANCH_ID, NAME, CITY, PHONE1, EMAIL FROM tbl_branch ORDER BY BRANCH_ID", nativeQuery = true)
    List<Object[]> findBranchIndex();
}