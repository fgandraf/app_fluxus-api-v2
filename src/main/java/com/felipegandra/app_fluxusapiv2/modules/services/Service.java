package com.felipegandra.app_fluxusapiv2.modules.services;

import com.felipegandra.app_fluxusapiv2.modules._domain.serviceOrders.ServiceOrder;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SERVICE")
    @SequenceGenerator(name = "SEQ_SERVICE", sequenceName = "SEQ_SERVICE", allocationSize = 1)
    public Long id;

    public String tag;
    public String description;

    @Column(name = "service_amount")
    public Double serviceAmount;

    @Column(name = "mileage_allowance")
    public Double mileageAllowance;

    @OneToMany(mappedBy = "service")
    private List<ServiceOrder> serviceOrders;
}
