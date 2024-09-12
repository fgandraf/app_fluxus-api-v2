package com.felipegandra.app_fluxusapiv2.modules.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT a.ORDER_ID, a.STATUS, a.PROFESSIONAL_ID, a.CITY, a.REFERENCE_CODE, a.CUSTOMER_NAME, a.DEADLINE, b.TAG " +
            "FROM TBL_ORDER a INNER JOIN TBL_SERVICE b ON b.SERVICE_ID = a.SERVICE_ID " +
            "WHERE a.INVOICE_ID is null ORDER BY a.ORDER_DATE", nativeQuery = true)
    List<Object[]> findOrdersFlow();

    @Query(value = "SELECT DISTINCT CITY FROM APP_USER.TBL_ORDER ORDER BY CITY", nativeQuery = true)
    List<Object[]> findCities();

    @Query(value = "SELECT ord.ORDER_ID, ord.ORDER_DATE, ord.REFERENCE_CODE, pro.TAG as PROFESSIONAL, ser.TAG as SERVICE, ord.CITY, ord.CUSTOMER_NAME, ord.SURVEY_DATE, ord.DONE_DATE, ord.SERVICE_AMOUNT, ord.MILEAGE_ALLOWANCE  " +
            "FROM TBL_ORDER ord " +
            "INNER JOIN TBL_SERVICE ser ON ser.SERVICE_ID = ord.SERVICE_ID " +
            "INNER JOIN TBL_PROFESSIONAL pro ON ord.PROFESSIONAL_ID = pro.PROFESSIONAL_ID " +
            "WHERE ord.INVOICED = 0 AND ord.STATUS = 3 " +
            "ORDER BY ord.ORDER_DATE", nativeQuery = true)
    List<Object[]> findDoneToInvoice();

    @Query(value = "SELECT ord.ORDER_ID, ord.STATUS, pro.TAG AS PROFESSIONAL, ord.ORDER_DATE, ord.REFERENCE_CODE, ser.TAG AS SERVICE, ord.CITY, ord.CUSTOMER_NAME, ord.SURVEY_DATE, ord.DONE_DATE, ord.INVOICED  " +
            "FROM TBL_ORDER ord " +
            "INNER JOIN TBL_SERVICE ser ON ser.SERVICE_ID = ord.SERVICE_ID " +
            "INNER JOIN TBL_PROFESSIONAL pro ON ord.PROFESSIONAL_ID = pro.PROFESSIONAL_ID " +
            "WHERE pro.TAG LIKE :professionalTag AND ser.TAG LIKE :serviceTag AND ord.CITY LIKE :orderCity AND ord.STATUS LIKE :orderStatus AND ord.INVOICED LIKE :orderInvoiced "  +
            "ORDER BY ord.ORDER_DATE", nativeQuery = true)
    List<Object[]> findFiltered(String professionalTag, String serviceTag, String orderCity, int orderStatus, int orderInvoiced);

    @Query(value = "SELECT ord.ORDER_ID, ord.ORDER_DATE, ord.REFERENCE_CODE, ord.PROFESSIONAL_ID, pro.TAG as PROFESSIONAL, ser.TAG as SERVICE, ord.CITY, ord.CUSTOMER_NAME, ord.SURVEY_DATE, ord.DONE_DATE, ord.INVOICE_ID, ord.SERVICE_AMOUNT, ord.MILEAGE_ALLOWANCE  " +
            "FROM TBL_ORDER ord " +
            "INNER JOIN TBL_SERVICE ser ON ser.SERVICE_ID = ord.SERVICE_ID " +
            "INNER JOIN TBL_PROFESSIONAL pro ON ord.PROFESSIONAL_ID = pro.PROFESSIONAL_ID " +
            "WHERE ord.INVOICE_ID = :invoiceId " +
            "ORDER BY ord.DONE_DATE", nativeQuery = true)
    List<Object[]> findInvoiced(Long invoiceId);

    @Query(value = "SELECT DISTINCT ord.PROFESSIONAL_ID, pro.PROFESSION, pro.NAME  " +
            "FROM TBL_ORDER ord " +
            "INNER JOIN TBL_PROFESSIONAL pro ON ord.PROFESSIONAL_ID = pro.PROFESSIONAL_ID " +
            "WHERE ord.INVOICE_ID = :invoiceId ", nativeQuery = true)
    List<Object[]> findProfessional(Long invoiceId);
}


