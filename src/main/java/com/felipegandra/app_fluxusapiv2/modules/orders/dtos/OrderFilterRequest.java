package com.felipegandra.app_fluxusapiv2.modules.orders.dtos;

public record OrderFilterRequest(
        String professionalTag,
        String serviceTag,
        String city,
        Integer status,
        boolean invoiced
) { }