package com.felipegandra.app_fluxusapiv2.modules.bankBranches;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, Long> { }


