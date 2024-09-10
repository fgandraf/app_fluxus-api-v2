package com.felipegandra.app_fluxusapiv2.modules.branches;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BranchService {

    private final BranchRepository repository;

    public BranchService(BranchRepository repository) {
        this.repository = repository;
    }

    public Branch findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Branch", id));
    }

    public Page<Branch> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Branch create(Branch branch) {
        return repository.save(branch);
    }

    public Branch update(Branch branch){
        return repository
                .findById(branch.getId())
                .map(foundBranch -> {
                    updateEntity(foundBranch, branch);
                    return repository.save(foundBranch);
                })
                .orElseThrow(() ->
                        new NotFoundException("Branch", branch.getId())
                );
    }

    public void delete(Long id) {
        var branch = repository.findById(id).orElseThrow(() -> new NotFoundException("Branch", id));
        repository.delete(branch);
    }

    private void updateEntity(Branch foundBranch, Branch updatedBranch) {
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