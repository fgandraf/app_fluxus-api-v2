package com.felipegandra.app_fluxusapiv2.modules.services;

import com.felipegandra.app_fluxusapiv2.exceptions.DatabaseOperationException;
import com.felipegandra.app_fluxusapiv2.exceptions.NoRecordsFoundException;
import com.felipegandra.app_fluxusapiv2.exceptions.ServiceNotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.services.dtos.ServiceCreateRequest;
import com.felipegandra.app_fluxusapiv2.modules.services.dtos.ServiceResponse;
import com.felipegandra.app_fluxusapiv2.modules.services.dtos.ServiceUpdateRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository repository;

    public ServiceService(ServiceRepository service) {
        this.repository = service;
    }

    public ServiceResponse findById(Long id) {
        var service = repository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));
        return new ServiceResponse(service);
    }

    public List<ServiceResponse> findAll() {
        try{
            var services = repository.findAll();
            if (services.isEmpty()) { throw new NoRecordsFoundException(); }

            List<ServiceResponse> responses = new ArrayList<>();
            services.forEach(service -> responses.add(new ServiceResponse(service)));

            return responses;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public ServiceResponse create(ServiceCreateRequest request) {
        try{
            var service = new com.felipegandra.app_fluxusapiv2.modules.services.Service(
                    null,
                    request.tag(),
                    request.description(),
                    request.serviceAmount(),
                    request.mileageAllowance()
            );

            var savedService = repository.save(service);
            return new ServiceResponse(savedService);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public ServiceResponse update(ServiceUpdateRequest request) {
        var service = repository.findById(request.id()).orElseThrow(() -> new ServiceNotFoundException(request.id()));

        try{
            service.setTag(request.tag());
            service.setDescription(request.description());
            service.setServiceAmount(request.serviceAmount());
            service.setMileageAllowance(request.mileageAllowance());

            var updatedService = repository.save(service);
            return new ServiceResponse(updatedService);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }

    }

    public void delete(Long id) {
        var service = repository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));

        try {
            repository.delete(service);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }
    }

}