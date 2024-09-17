package com.felipegandra.app_fluxusapiv2.modules.branches.dtos;

import com.felipegandra.app_fluxusapiv2.modules.branches.Branch;

public record BranchResponse(
        String id,
        String name,
        String address,
        String complement,
        String district,
        String city,
        String zip,
        String state,
        String contactName,
        String phone1,
        String phone2,
        String email
) {
    public BranchResponse(Branch branch) {
        this(
                branch.getId(),
                branch.getName(),
                branch.getAddress(),
                branch.getComplement(),
                branch.getDistrict(),
                branch.getCity(),
                branch.getZip(),
                branch.getState(),
                branch.getContactName(),
                branch.getPhone1(),
                branch.getPhone2(),
                branch.getEmail()
        );
    }
}