package com.felipegandra.app_fluxusapiv2.modules.orders.dtos;

import com.felipegandra.app_fluxusapiv2.modules.orders.Order;
import java.time.LocalDate;

public record OrderResponse(
        Long id,
        String referenceCode,
        String branchId,
        LocalDate orderDate,
        LocalDate deadline,
        Long professionalId,
        Long serviceId,
        Double serviceAmount,
        Double mileageAllowance,
        Boolean siopi,
        String customerName,
        String city,
        String contactName,
        String contactPhone,
        String coordinates,
        int status,
        LocalDate pendingDate,
        LocalDate surveyDate,
        LocalDate doneDate,
        String comments
) {

    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getReferenceCode(),
                order.getBranchId(),
                order.getOrderDate(),
                order.getDeadline(),
                order.getProfessionalId(),
                order.getServiceId(),
                order.getServiceAmount(),
                order.getMileageAllowance(),
                order.getSiopi(),
                order.getCustomerName(),
                order.getCity(),
                order.getContactName(),
                order.getContactPhone(),
                order.getCoordinates(),
                order.getStatus().getValue(),
                order.getPendingDate(),
                order.getSurveyDate(),
                order.getDoneDate(),
                order.getComments()
        );
    }
}