package com.felipegandra.app_fluxusapiv2.modules.invoices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query(value = "SELECT DESCRIPTION FROM TBL_INVOICE WHERE INVOICE_ID = :invoiceId", nativeQuery = true)
    Optional<String> findDescriptionById(Long invoiceId);

    @Modifying
    @Query(value = "UPDATE TBL_INVOICE SET SUBTOTAL_SERVICE = :subtotalService, SUBTOTAL_MILEAGE = :subtotalMileage, TOTAL = :total WHERE INVOICE_ID = :invoiceId", nativeQuery = true)
    int updateTotalService(Double subtotalService, Double subtotalMileage, Double total, Long invoiceId);
}