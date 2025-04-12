package com.felipegandra.app_fluxusapiv2.modules.branches.dtos;

import com.felipegandra.app_fluxusapiv2.modules.branches.Branch;

public record BranchIndexResponse(
        String id,
        String name,
        String city,
        String phone1,
        String email
) {
    public BranchIndexResponse(Branch branch) {
        this(
                branch.getId(),
                branch.getName(),
                branch.getCity(),
                branch.getPhone1(),
                branch.getEmail()
        );
    }
}
