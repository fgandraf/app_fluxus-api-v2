package com.felipegandra.app_fluxusapiv2.modules.bankBranches;

import com.felipegandra.app_fluxusapiv2.modules._domain.serviceOrders.ServiceOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "tbl_bank_branch")
@Getter
@Setter
@EqualsAndHashCode
public class BankBranch {

    @Id
    @Column(name = "bank_branch_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BANK_BRANCH")
    @SequenceGenerator(name = "SEQ_BANK_BRANCH", sequenceName = "SEQ_BANK_BRANCH", allocationSize = 1)
    public Long id;

    @NotNull
    public String name;

    public String address;
    public String complement;
    public String district;
    public String city;
    public String zip;
    public String state;

    @Column(name = "contact_name")
    public String contactName;

    @Size(max = 11)
    public String phone1;

    @Size(max = 11)
    public String phone2;

    @Email
    public String email;

    @OneToMany(mappedBy = "branch")
    private List<ServiceOrder> serviceOrders;

    public BankBranch(Long id, String name, String address, String complement, String district, String city, String zip, String state, String contactName, String phone1, String phone2, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.complement = complement;
        this.district = district;
        this.city = city;
        this.zip = zip;
        this.state = state;
        this.contactName = contactName;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
    }


    public BankBranch(BankBranch bankBranch) {
        this(
                bankBranch.getId(),
                bankBranch.getName(),
                bankBranch.getAddress(),
                bankBranch.getComplement(),
                bankBranch.getDistrict(),
                bankBranch.getCity(),
                bankBranch.getZip(),
                bankBranch.getState(),
                bankBranch.getContactName(),
                bankBranch.getPhone1(),
                bankBranch.getPhone2(),
                bankBranch.getEmail()
        );
    }


    public BankBranch() { }
}
