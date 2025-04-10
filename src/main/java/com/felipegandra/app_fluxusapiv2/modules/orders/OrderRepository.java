package com.felipegandra.app_fluxusapiv2.modules.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT a.ORDER_ID, a.STATUS, a.PROFESSIONAL_ID, a.CITY, a.REFERENCE_CODE, a.CUSTOMER_NAME, a.DEADLINE, b.TAG " +
            "FROM tbl_order a INNER JOIN tbl_service b ON b.SERVICE_ID = a.SERVICE_ID " +
            "WHERE a.INVOICE_ID is null ORDER BY a.ORDER_DATE", nativeQuery = true)
    Optional<List<Object[]>> findOrdersFlow();

    @Query(value = "SELECT DISTINCT CITY FROM tbl_order ORDER BY CITY", nativeQuery = true)
    Optional<List<Object[]>> findCities();

    @Query(value = "SELECT ord.ORDER_ID, ord.ORDER_DATE, ord.REFERENCE_CODE, pro.TAG as PROFESSIONAL, ser.TAG as SERVICE, ord.CITY, ord.CUSTOMER_NAME, ord.SURVEY_DATE, ord.DONE_DATE, ord.SERVICE_AMOUNT, ord.MILEAGE_ALLOWANCE  " +
            "FROM tbl_order ord " +
            "INNER JOIN tbl_service ser ON ser.SERVICE_ID = ord.SERVICE_ID " +
            "INNER JOIN tbl_professional pro ON ord.PROFESSIONAL_ID = pro.PROFESSIONAL_ID " +
            "WHERE ord.INVOICED = 0 AND ord.STATUS = 3 " +
            "ORDER BY ord.ORDER_DATE", nativeQuery = true)
    Optional<List<Object[]>> findDoneToInvoice();


    @Query(value = "SELECT ord.ORDER_ID, ord.STATUS, pro.TAG AS PROFESSIONAL, ord.ORDER_DATE, ord.REFERENCE_CODE, ser.TAG AS SERVICE, ord.CITY, ord.CUSTOMER_NAME, ord.SURVEY_DATE, ord.DONE_DATE, ord.INVOICED " +
            "FROM tbl_order ord " +
            "INNER JOIN tbl_service ser ON ser.SERVICE_ID = ord.SERVICE_ID " +
            "INNER JOIN tbl_professional pro ON ord.PROFESSIONAL_ID = pro.PROFESSIONAL_ID " +
            "WHERE (:professionalTag IS NULL OR pro.TAG LIKE :professionalTag) " +
            "AND (:serviceTag IS NULL OR ser.TAG LIKE :serviceTag) " +
            "AND (:orderCity IS NULL OR ord.CITY LIKE :orderCity) " +
            "AND (:orderStatus IS NULL OR ord.STATUS = :orderStatus) " +
            "AND (:orderInvoiced IS NULL OR ord.INVOICED = :orderInvoiced) " +
            "ORDER BY ord.ORDER_DATE", nativeQuery = true)
    Optional<List<Object[]>> findFiltered(
            @Param("professionalTag") String professionalTag,
            @Param("serviceTag") String serviceTag,
            @Param("orderCity") String orderCity,
            @Param("orderStatus") Integer orderStatus,  // Mudei para Integer para aceitar null
            @Param("orderInvoiced") Boolean orderInvoiced  // Mudei para Boolean
    );






    @Query(value = "SELECT ord.ORDER_ID, ord.ORDER_DATE, ord.REFERENCE_CODE, ord.PROFESSIONAL_ID, pro.TAG as PROFESSIONAL, ser.TAG as SERVICE, ord.CITY, ord.CUSTOMER_NAME, ord.SURVEY_DATE, ord.DONE_DATE, ord.INVOICE_ID, ord.SERVICE_AMOUNT, ord.MILEAGE_ALLOWANCE  " +
            "FROM tbl_order ord " +
            "INNER JOIN tbl_service ser ON ser.SERVICE_ID = ord.SERVICE_ID " +
            "INNER JOIN tbl_professional pro ON ord.PROFESSIONAL_ID = pro.PROFESSIONAL_ID " +
            "WHERE ord.INVOICE_ID = :invoiceId " +
            "ORDER BY ord.DONE_DATE", nativeQuery = true)
    Optional<List<Object[]>> findInvoiced(Long invoiceId);

    @Query(value = "SELECT DISTINCT ord.PROFESSIONAL_ID, pro.PROFESSION, pro.NAME  " +
            "FROM tbl_order ord " +
            "INNER JOIN tbl_professional pro ON ord.PROFESSIONAL_ID = pro.PROFESSIONAL_ID " +
            "WHERE ord.INVOICE_ID = :invoiceId ", nativeQuery = true)
    Optional<List<Object[]>> findProfessional(Long invoiceId);
}


