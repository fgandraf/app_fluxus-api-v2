package com.felipegandra.app_fluxusapiv2.modules.invoices;

import com.felipegandra.app_fluxusapiv2.exceptions.InvoiceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    public List<Invoice> findAll() {
        return repository.findAll();
    }

    public Optional<String> getDescription(Long id){
        return repository.findDescriptionById(id);
    }

    public Invoice create(Invoice bankBranch) {
        return repository.save(bankBranch);
    }

    @Transactional
    public int updateTotal(Invoice invoice) {
        return repository.updateTotalService(
                invoice.getSubtotalService(),
                invoice.getSubtotalMileage(),
                invoice.getTotal(),
                invoice.getId()
        );
    }

    public void delete(Long id) {
        var invoice = repository.findById(id).orElseThrow(() -> new InvoiceNotFoundException(id));
        repository.delete(invoice);
    }

}