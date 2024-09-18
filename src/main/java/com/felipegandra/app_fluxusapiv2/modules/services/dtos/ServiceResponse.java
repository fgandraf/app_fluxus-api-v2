package com.felipegandra.app_fluxusapiv2.modules.services.dtos;

import com.felipegandra.app_fluxusapiv2.modules.services.Service;

public record ServiceResponse(
        Long id,
        String tag,
        String description,
        Double serviceAmount,
        Double mileageAllowance
) {
    public ServiceResponse(Service service){
        this(
                service.getId(),
                service.getTag(),
                service.getDescription(),
                service.serviceAmount,
                service.mileageAllowance
        );
    }
}