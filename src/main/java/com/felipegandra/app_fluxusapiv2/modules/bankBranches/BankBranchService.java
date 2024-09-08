package com.felipegandra.app_fluxusapiv2.modules.bankBranches;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BankBranchService {

    private final BankBranchRepository repository;

    public BankBranchService(BankBranchRepository repository) {
        this.repository = repository;
    }

    public BankBranch findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Bank Branch", id));
    }

    public Page<BankBranch> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public BankBranch create(BankBranch bankBranch) {
        return repository.save(bankBranch);
    }

    public BankBranch update(BankBranch bankBranch){
        return repository
                .findById(bankBranch.getId())
                .map(foundBranch -> {
                    updateEntity(foundBranch, bankBranch);
                    return repository.save(foundBranch);
                })
                .orElseThrow(() ->
                        new NotFoundException("Bank Branch", bankBranch.getId())
                );
    }

    public void delete(Long id) {
        var branch = repository.findById(id).orElseThrow(() -> new NotFoundException("Bank Branch", id));
        repository.delete(branch);
    }

    private void updateEntity(BankBranch foundBranch, BankBranch updatedBranch) {
        foundBranch.setName(updatedBranch.getName());
        foundBranch.setAddress(updatedBranch.getAddress());
        foundBranch.setComplement(updatedBranch.getComplement());
        foundBranch.setDistrict(updatedBranch.getDistrict());
        foundBranch.setCity(updatedBranch.getCity());
        foundBranch.setZip(updatedBranch.getZip());
        foundBranch.setState(updatedBranch.getState());
        foundBranch.setContactName(updatedBranch.getContactName());
        foundBranch.setPhone1(updatedBranch.getPhone1());
        foundBranch.setPhone2(updatedBranch.getPhone2());
        foundBranch.setEmail(updatedBranch.getEmail());
    }
}