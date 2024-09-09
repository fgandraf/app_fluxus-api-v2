package com.felipegandra.app_fluxusapiv2.modules.services;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServiceService {

    private final ServiceRepository repository;

    public ServiceService(ServiceRepository service) {
        this.repository = service;
    }

    public com.felipegandra.app_fluxusapiv2.modules.services.Service findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Service", id));
    }

    public Page<com.felipegandra.app_fluxusapiv2.modules.services.Service> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public com.felipegandra.app_fluxusapiv2.modules.services.Service create(com.felipegandra.app_fluxusapiv2.modules.services.Service service) {
        return repository.save(service);
    }

    public com.felipegandra.app_fluxusapiv2.modules.services.Service update(com.felipegandra.app_fluxusapiv2.modules.services.Service service) {
        return repository
                .findById(service.getId())
                .map(foundService -> {
                    updateEntity(foundService, service);
                    return repository.save(foundService);
                })
                .orElseThrow(() ->
                        new NotFoundException("Service", service.getId())
                );
    }

    public void delete(Long id) {
        var service = repository.findById(id).orElseThrow(() -> new NotFoundException("Service", id));
        repository.delete(service);
    }

    private void updateEntity(com.felipegandra.app_fluxusapiv2.modules.services.Service foundService, com.felipegandra.app_fluxusapiv2.modules.services.Service updatedService) {
        foundService.setTag(updatedService.getTag());
        foundService.setDescription(updatedService.getDescription());
        foundService.setServiceAmount(updatedService.getServiceAmount());
        foundService.setMileageAllowance(updatedService.getMileageAllowance());
    }
}