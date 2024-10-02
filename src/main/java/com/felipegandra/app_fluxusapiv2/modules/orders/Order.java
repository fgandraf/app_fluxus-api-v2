package com.felipegandra.app_fluxusapiv2.modules.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipegandra.app_fluxusapiv2.modules.branches.Branch;
import com.felipegandra.app_fluxusapiv2.modules.invoices.Invoice;
import com.felipegandra.app_fluxusapiv2.modules.orders.enums.Status;
import com.felipegandra.app_fluxusapiv2.modules.professionals.Professional;
import com.felipegandra.app_fluxusapiv2.modules.services.Service;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_order")
@Getter
@Setter
@EqualsAndHashCode
public class Order
{
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "reference_code")
    public String referenceCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "branch_id")
    public Branch branch;

    @JsonProperty("branch_id")
    public String getBranchId() {
        return branch != null ? branch.getId() : null;
    }

    @Column(name = "order_date")
    public LocalDate orderDate;

    public LocalDate deadline;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "professional_id")
    public Professional professional;

    @JsonProperty("professional_id")
    public Long getProfessionalId() {
        return professional != null ? professional.getId() : null;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "service_id")
    public Service service;

    @JsonProperty("service_id")
    public Long getServiceId() {
        return service != null ? service.getId() : null;
    }

    @Column(name = "service_amount")
    public Double serviceAmount;

    @Column(name = "mileage_allowance")
    public Double mileageAllowance;

    public Boolean siopi;

    @Column(name = "customer_name")
    public String customerName;

    public String city;

    @Column(name = "contact_name")
    public String contactName;

    @Column(name = "contact_phone")
    public String contactPhone;

    public String coordinates;

    @Enumerated(EnumType.ORDINAL)
    public Status status;

    @Column(name = "pending_date")
    public LocalDate pendingDate;

    @Column(name = "survey_date")
    public LocalDate surveyDate;

    @Column(name = "done_date")
    public LocalDate doneDate;

    public String comments;

    public Boolean invoiced;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    public Invoice invoice;

    @JsonProperty("invoice_id")
    public Long getInvoiceId() {
        return invoice != null ? invoice.getId() : null;
    }

    public Order(Long id, String referenceCode, Branch branch, LocalDate orderDate, LocalDate deadline, Professional professional, Service service, Double serviceAmount, Double mileageAllowance, Boolean siopi, String customerName, String city, String contactName, String contactPhone, String coordinates, Status status, LocalDate pendingDate, LocalDate surveyDate, LocalDate doneDate, String comments, Boolean invoiced, Invoice invoice) {
        this.id = id;
        this.referenceCode = referenceCode;
        this.branch = branch;
        this.orderDate = orderDate;
        this.deadline = deadline;
        this.professional = professional;
        this.service = service;
        this.serviceAmount = serviceAmount;
        this.mileageAllowance = mileageAllowance;
        this.siopi = siopi;
        this.customerName = customerName;
        this.city = city;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.coordinates = coordinates;
        this.status = status;
        this.pendingDate = pendingDate;
        this.surveyDate = surveyDate;
        this.doneDate = doneDate;
        this.comments = comments;
        this.invoiced = invoiced;
        this.invoice = invoice;
    }

    public Order() { }
}