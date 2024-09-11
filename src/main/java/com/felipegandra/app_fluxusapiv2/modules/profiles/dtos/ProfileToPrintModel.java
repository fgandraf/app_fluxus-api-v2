package com.felipegandra.app_fluxusapiv2.modules.profiles.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileToPrintModel {
    public String cnpj;
    public String companyName;
    public String contractNotice;
    public String contractNumber;
    public String logo;
}
