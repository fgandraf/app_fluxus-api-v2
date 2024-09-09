package com.felipegandra.app_fluxusapiv2.modules.invoices;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    public Invoice findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Invoice", id));
    }

    public Page<Invoice> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Invoice create(Invoice bankBranch) {
        return repository.save(bankBranch);
    }

    public Invoice update(Invoice invoice){
        return repository
                .findById(invoice.getId())
                .map(foundInvoice -> {
                    updateEntity(foundInvoice, invoice);
                    return repository.save(foundInvoice);
                })
                .orElseThrow(() ->
                        new NotFoundException("Invoice", invoice.getId())
                );
    }

    public void delete(Long id) {
        var invoice = repository.findById(id).orElseThrow(() -> new NotFoundException("Invoice", id));
        repository.delete(invoice);
    }

    private void updateEntity(Invoice foundInvoice, Invoice updatedInvoice) {
        foundInvoice.setDescription(updatedInvoice.getDescription());
        foundInvoice.setIssueDate(updatedInvoice.getIssueDate());
        foundInvoice.setSubtotalService(updatedInvoice.getSubtotalService());
        foundInvoice.setSubtotalMileage(updatedInvoice.getSubtotalMileage());
        foundInvoice.setTotal(updatedInvoice.getTotal());
    }

}