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
}
