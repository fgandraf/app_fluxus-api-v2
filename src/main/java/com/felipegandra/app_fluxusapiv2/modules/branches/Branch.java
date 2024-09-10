package com.felipegandra.app_fluxusapiv2.modules.branches;

import com.felipegandra.app_fluxusapiv2.modules.orders.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "tbl_branch")
@Getter
@Setter
@EqualsAndHashCode
public class Branch {

    @Id
    @Column(name = "branch_id")
    public String id;

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
    private List<Order> orders;

    public Branch(String id, String name, String address, String complement, String district, String city, String zip, String state, String contactName, String phone1, String phone2, String email) {
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


    public Branch(Branch branch) {
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


    public Branch() { }
}
