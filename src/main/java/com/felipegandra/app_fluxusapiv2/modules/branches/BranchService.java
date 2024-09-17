package com.felipegandra.app_fluxusapiv2.modules.branches;

import com.felipegandra.app_fluxusapiv2.exceptions.BranchNotFoundException;
import com.felipegandra.app_fluxusapiv2.exceptions.DatabaseOperationException;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchCreateRequest;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchIndexResponse;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchResponse;
import com.felipegandra.app_fluxusapiv2.modules.branches.dtos.BranchUpdateRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {

    private final BranchRepository repository;

    public BranchService(BranchRepository repository) {
        this.repository = repository;
    }

    public List<BranchIndexResponse> getBranchIndex() {
        List<BranchIndexResponse> branches = new ArrayList<>();

        try{
            repository.findBranchIndex()
                    .forEach(result -> {
                        var branch = new BranchIndexResponse(
                                (String) result[0],
                                (String) result[1],
                                (String) result[2],
                                (String) result[3],
                                (String) result[4]
                        );

                        branches.add(branch);
                    });

            return branches;

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }

    }

    public BranchResponse findById(String id) {
        var branch = repository.findById(id).orElseThrow(() -> new BranchNotFoundException(id));
        return new BranchResponse(branch);
    }

    public BranchResponse getBranchDetailsById(String branchId) {
        var branch = repository.findBranchDetailsById(branchId).orElseThrow(() -> new BranchNotFoundException(branchId));
        return new BranchResponse(branch);
    }

    public BranchResponse create(BranchCreateRequest request) {

        try{
            var branch = new Branch(
                    null,
                    request.name(),
                    request.address(),
                    request.complement(),
                    request.district(),
                    request.city(),
                    request.zip(),
                    request.state(),
                    request.contactName(),
                    request.phone1(),
                    request.phone2(),
                    request.email()
            );
            var savedBranch = repository.save(branch);
            return new BranchResponse(savedBranch);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }

    }

    public BranchResponse update(BranchUpdateRequest request){
        var branch = repository.findById(request.id()).orElseThrow(() -> new BranchNotFoundException(request.id()));

        try{
            branch.setName(request.name());
            branch.setAddress(request.address());
            branch.setComplement(request.complement());
            branch.setDistrict(request.district());
            branch.setCity(request.city());
            branch.setZip(request.zip());
            branch.setState(request.state());
            branch.setContactName(request.contactName());
            branch.setPhone1(request.phone1());
            branch.setPhone2(request.phone2());
            branch.setEmail(request.email());

            var savedBranch = repository.save(branch);
            return new BranchResponse(savedBranch);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }

    }

    public void delete(String id) {
        var branch = repository.findById(id).orElseThrow(() -> new BranchNotFoundException(id));

        try {
            repository.delete(branch);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }

    }
}