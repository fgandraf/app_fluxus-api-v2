package com.felipegandra.app_fluxusapiv2.modules.profiles;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_profile")
@Getter
@Setter
@EqualsAndHashCode
public class Profile
{
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFILE")
    @SequenceGenerator(name = "SEQ_PROFILE", sequenceName = "SEQ_PROFILE", allocationSize = 1)
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
    public LocalDateTime establishmentDate;

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
    public LocalDateTime contractEstablished;

    @Column(name = "contract_start")
    public LocalDateTime contractStart;

    @Column(name = "contract_end")
    public LocalDateTime contractEnd;
}
