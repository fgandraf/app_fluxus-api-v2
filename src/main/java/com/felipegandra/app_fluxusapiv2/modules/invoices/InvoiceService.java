package com.felipegandra.app_fluxusapiv2.modules.invoices;

import com.felipegandra.app_fluxusapiv2.exceptions.DatabaseOperationException;
import com.felipegandra.app_fluxusapiv2.exceptions.InvoiceNotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceCreateRequest;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceDescriptionResponse;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceResponse;
import com.felipegandra.app_fluxusapiv2.modules.invoices.dtos.InvoiceUpdateRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) { this.repository = repository; }

    public List<InvoiceResponse> findAll() {
        List<InvoiceResponse> invoices = new ArrayList<>();
        try{
            repository.findAll().forEach(result -> invoices.add(new InvoiceResponse(result)));
            return invoices;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public InvoiceDescriptionResponse getDescription(Long id){
        var invoice = repository.findById(id).orElseThrow(() -> new InvoiceNotFoundException(id));
        return new InvoiceDescriptionResponse(invoice.getDescription());
    }

    public InvoiceResponse create(InvoiceCreateRequest request) {
        try{
            var invoice = new Invoice(
                    null,
                    request.description(),
                    request.issueDate(),
                    request.subtotalService(),
                    request.subtotalMileage(),
                    request.total()
            );
            var savedInvoice = repository.save(invoice);
            return new InvoiceResponse(savedInvoice);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public InvoiceResponse updateTotal(InvoiceUpdateRequest request) {
        var invoice = repository.findById(request.id()).orElseThrow(() -> new InvoiceNotFoundException(request.id()));

        try{
            invoice.setSubtotalService(request.subtotalService());
            invoice.setSubtotalMileage(request.subtotalMileage());
            invoice.setTotal(request.total());

            var savedInvoice = repository.save(invoice);
            return new InvoiceResponse(savedInvoice);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public void delete(Long id) {
        var invoice = repository.findById(id).orElseThrow(() -> new InvoiceNotFoundException(id));
        try {
            repository.delete(invoice);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }
    }

}