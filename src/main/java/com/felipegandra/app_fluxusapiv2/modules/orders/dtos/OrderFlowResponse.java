package com.felipegandra.app_fluxusapiv2.modules.orders.dtos;

public record OrderFlowResponse(
        Long id,
        String title,
        int status,
        Long professionalId
) {
}
