package com.felipegandra.app_fluxusapiv2.modules.orders.dtos;

public record OrderFlowOutput(
        Long id,
        String[] card,
        int status,
        Long professionalId
) {
}
