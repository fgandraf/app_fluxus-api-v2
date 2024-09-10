package com.felipegandra.app_fluxusapiv2.modules.branches;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BranchService {

    private final BranchRepository repository;

    public BranchService(BranchRepository repository) {
        this.repository = repository;
    }

    public List<BranchDetails> getBranchIndex() {
        return repository.findBranchIndex()
                .stream()
                .map(result -> new BranchDetails(
                        (String) result[0],
                        (String) result[1],
                        (String) result[2],
                        (String) result[3],
                        (String) result[4]
                ))
                .collect(Collectors.toList());
    }

    public Branch findById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Branch", id));
    }

    public Optional<BranchDetails> getBranchDetailsById(String branchId) {
        return repository.findBranchDetailsById(branchId);
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

    public void delete(String id) {
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