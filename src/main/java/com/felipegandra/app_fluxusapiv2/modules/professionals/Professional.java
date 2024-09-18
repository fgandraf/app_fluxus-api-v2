package com.felipegandra.app_fluxusapiv2.modules.professionals;

import com.felipegandra.app_fluxusapiv2.modules.orders.Order;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_professional")
@Getter
@Setter
@EqualsAndHashCode
public class Professional
{
    @Id
    @Column(name = "professional_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFESSIONAL")
    @SequenceGenerator(name = "SEQ_PROFESSIONAL", sequenceName = "SEQ_PROFESSIONAL", allocationSize = 1)
    public Long id;

    public String tag;
    public String name;
    public String cpf;
    public LocalDate birthday;
    public String profession;

    @Column(name = "permit_number")
    public String permitNumber;

    public String association;
    public String phone1;
    public String phone2;
    public String email;

    @OneToMany(mappedBy = "professional")
    private List<Order> orders;

    public Professional(Long id, String tag, String name, String cpf, LocalDate birthday, String profession, String permitNumber, String association, String phone1, String phone2, String email) {
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.cpf = cpf;
        this.birthday = birthday;
        this.profession = profession;
        this.permitNumber = permitNumber;
        this.association = association;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
    }

    public Professional() {}
}
