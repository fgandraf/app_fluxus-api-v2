package com.felipegandra.app_fluxusapiv2.modules.branches.dtos;

public record BranchIndexResponse(
        String id,
        String name,
        String city,
        String phone1,
        String email
) {
}
