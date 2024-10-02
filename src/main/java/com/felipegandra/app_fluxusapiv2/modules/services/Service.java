package com.felipegandra.app_fluxusapiv2.modules.services;

import com.felipegandra.app_fluxusapiv2.modules.orders.Order;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "tbl_service")
@Getter
@Setter
@EqualsAndHashCode
public class Service
{
    @Id
    @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String tag;
    public String description;

    @Column(name = "service_amount")
    public Double serviceAmount;

    @Column(name = "mileage_allowance")
    public Double mileageAllowance;

    @OneToMany(mappedBy = "service")
    private List<Order> orders;

    public Service(Long id, String tag, String description, Double serviceAmount, Double mileageAllowance) {
        this.id = id;
        this.tag = tag;
        this.description = description;
        this.serviceAmount = serviceAmount;
        this.mileageAllowance = mileageAllowance;
    }

    public Service(){}
}
