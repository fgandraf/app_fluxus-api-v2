package com.felipegandra.app_fluxusapiv2.modules.professionals;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalService {

    private final ProfessionalRepository repository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.repository = professionalRepository;
    }

    public Professional findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Professional", id));
    }

    public Page<Professional> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Professional create(Professional bankBranch) {
        return repository.save(bankBranch);
    }

    public Professional update(Professional professional) {
        return repository
                .findById(professional.getId())
                .map(foundProfessional -> {
                    updateEntity(foundProfessional, professional);
                    return repository.save(foundProfessional);
                })
                .orElseThrow(() ->
                        new NotFoundException("Professional", professional.getId())
                );
    }

    public void delete(Long id) {
        var professional = repository.findById(id).orElseThrow(() -> new NotFoundException("Professional", id));
        repository.delete(professional);
    }

    private void updateEntity(Professional foundProfessional, Professional updatedProfessional) {
        foundProfessional.setTag(updatedProfessional.getTag());
        foundProfessional.setName(updatedProfessional.getName());
        foundProfessional.setCpf(updatedProfessional.getCpf());
        foundProfessional.setBirthday(updatedProfessional.getBirthday());
        foundProfessional.setProfession(updatedProfessional.getProfession());
        foundProfessional.setPermitNumber(updatedProfessional.getPermitNumber());
        foundProfessional.setAssociation(updatedProfessional.getAssociation());
        foundProfessional.setPhone1(updatedProfessional.getPhone1());
        foundProfessional.setPhone2(updatedProfessional.getPhone2());
        foundProfessional.setEmail(updatedProfessional.getEmail());
    }

}