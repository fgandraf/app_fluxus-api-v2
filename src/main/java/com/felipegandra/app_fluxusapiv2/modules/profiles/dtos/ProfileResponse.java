package com.felipegandra.app_fluxusapiv2.modules.profiles.dtos;

import com.felipegandra.app_fluxusapiv2.modules.profiles.Profile;
import java.time.LocalDate;

public record ProfileResponse(
        Long id,
        String cnpj,
        String tradingName,
        String companyName,
        String stateId,
        String cityId,
        String address,
        String complement,
        String district,
        String city,
        String zip,
        String state,
        LocalDate establishmentDate,
        String phone1,
        String phone2,
        String email,
        String bankAccountName,
        String bankAccountType,
        String bankAccountBranch,
        String bankAccountDigit,
        String bankAccountNumber,
        String contractorName,
        String contractNotice,
        String contractNumber,
        LocalDate contractEstablished,
        LocalDate contractStart,
        LocalDate contractEnd
) {

    public ProfileResponse(Profile profile){
        this(
                profile.getId(),
                profile.getCnpj(),
                profile.getTradingName(),
                profile.getCompanyName(),
                profile.getStateId(),
                profile.getCityId(),
                profile.getAddress(),
                profile.getComplement(),
                profile.getDistrict(),
                profile.getCity(),
                profile.getZip(),
                profile.getState(),
                profile.getEstablishmentDate(),
                profile.getPhone1(),
                profile.getPhone2(),
                profile.getEmail(),
                profile.getBankAccountName(),
                profile.getBankAccountType(),
                profile.getBankAccountBranch(),
                profile.getBankAccountDigit(),
                profile.getBankAccountNumber(),
                profile.getContractorName(),
                profile.getContractNotice(),
                profile.getContractNumber(),
                profile.getContractEstablished(),
                profile.getContractStart(),
                profile.getContractEnd()
        );
    }
}
