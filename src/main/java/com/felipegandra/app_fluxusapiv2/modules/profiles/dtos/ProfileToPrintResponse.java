package com.felipegandra.app_fluxusapiv2.modules.profiles.dtos;

public record ProfileToPrintResponse (
     String cnpj,
     String companyName,
     String contractNotice,
     String contractNumber,
     String logo
){
}
