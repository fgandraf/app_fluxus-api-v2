package com.felipegandra.app_fluxusapiv2.modules.invoices;

import com.felipegandra.app_fluxusapiv2.modules.orders.Order;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_invoice")
@Getter
@Setter
@EqualsAndHashCode
public class Invoice
{
    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String description;

    @Column(name = "issue_date")
    public LocalDateTime issueDate;

    @Column(name = "subtotal_service")
    public Double subtotalService;

    @Column(name = "subtotal_mileage")
    public Double subtotalMileage;

    public Double total;

    @OneToMany(mappedBy = "invoice")
    private List<Order> orders;

    public Invoice(Long id, String description, LocalDateTime issueDate, Double subtotalService, Double subtotalMileage, Double total) {
        this.id = id;
        this.description = description;
        this.issueDate = issueDate;
        this.subtotalService = subtotalService;
        this.subtotalMileage = subtotalMileage;
        this.total = total;
    }

    public Invoice(){}
}