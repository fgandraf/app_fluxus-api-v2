package com.felipegandra.app_fluxusapiv2.modules.profiles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_profile")
@Getter
@Setter
@EqualsAndHashCode
public class Profile
{
    @Id
    @Column(name = "profile_id")
    public Long id;

    public String cnpj;

    @Column(name = "trading_name")
    public String tradingName;

    @Column(name = "company_name")
    public String companyName;

    @Column(name = "state_id")
    public String stateId;

    @Column(name = "city_id")
    public String cityId;

    public String address;
    public String complement;
    public String district;
    public String city;
    public String zip;
    public String state;

    @Column(name = "establishment_date")
    public LocalDate establishmentDate;

    @Size(max = 11)
    public String phone1;

    @Size(max = 11)
    public String phone2;

    public String email;

    @Column(name = "bank_account_name")
    public String bankAccountName;

    @Column(name = "bank_account_type")
    public String bankAccountType;

    @Column(name = "bank_account_branch")
    public String bankAccountBranch;

    @Column(name = "bank_account_digit")
    public String bankAccountDigit;

    @Column(name = "bank_account_number")
    public String bankAccountNumber;

    @Column(name = "contractor_name")
    public String contractorName;

    @Column(name = "contract_notice")
    public String contractNotice;

    @Column(name = "contract_number")
    public String contractNumber;

    @Column(name = "contract_established")
    public LocalDate contractEstablished;

    @Column(name = "contract_start")
    public LocalDate contractStart;

    @Column(name = "contract_end")
    public LocalDate contractEnd;


    public Profile(Long id, String cnpj, String tradingName, String companyName, String stateId, String cityId, String address, String complement, String district, String city, String zip, String state, LocalDate establishmentDate, String phone1, String phone2, String email, String bankAccountName, String bankAccountType, String bankAccountBranch, String bankAccountDigit, String bankAccountNumber, String contractorName, String contractNotice, String contractNumber, LocalDate contractEstablished, LocalDate contractStart, LocalDate contractEnd) {
        this.id = id;
        this.cnpj = cnpj;
        this.tradingName = tradingName;
        this.companyName = companyName;
        this.stateId = stateId;
        this.cityId = cityId;
        this.address = address;
        this.complement = complement;
        this.district = district;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.establishmentDate = establishmentDate;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.bankAccountName = bankAccountName;
        this.bankAccountType = bankAccountType;
        this.bankAccountBranch = bankAccountBranch;
        this.bankAccountDigit = bankAccountDigit;
        this.bankAccountNumber = bankAccountNumber;
        this.contractorName = contractorName;
        this.contractNotice = contractNotice;
        this.contractNumber = contractNumber;
        this.contractEstablished = contractEstablished;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
    }

    public Profile(){}
}
