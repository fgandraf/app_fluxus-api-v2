package com.felipegandra.app_fluxusapiv2.modules.profiles;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public Profile findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Bank Branch", id));
    }

    public Profile create(Profile bankBranch) {
        return repository.save(bankBranch);
    }

    public Profile update(Profile profile){
        return repository
                .findById(profile.getId())
                .map(foundProfile -> {
                    updateEntity(foundProfile, profile);
                    return repository.save(foundProfile);
                })
                .orElseThrow(() ->
                        new NotFoundException("Profile", profile.getId())
                );
    }

    private void updateEntity(Profile foundProfile, Profile updatedProfile) {
        foundProfile.setCnpj(updatedProfile.getCnpj());
        foundProfile.setTradingName(updatedProfile.getTradingName());
        foundProfile.setCompanyName(updatedProfile.getCompanyName());
        foundProfile.setStateId(updatedProfile.getStateId());
        foundProfile.setCityId(updatedProfile.getCityId());
        foundProfile.setAddress(updatedProfile.getAddress());
        foundProfile.setComplement(updatedProfile.getComplement());
        foundProfile.setDistrict(updatedProfile.getDistrict());
        foundProfile.setCity(updatedProfile.getCity());
        foundProfile.setZip(updatedProfile.getZip());
        foundProfile.setState(updatedProfile.getState());
        foundProfile.setEstablishmentDate(updatedProfile.getEstablishmentDate());
        foundProfile.setPhone1(updatedProfile.getPhone1());
        foundProfile.setPhone2(updatedProfile.getPhone2());
        foundProfile.setEmail(updatedProfile.getEmail());
        foundProfile.setBankAccountName(updatedProfile.getBankAccountName());
        foundProfile.setBankAccountType(updatedProfile.getBankAccountType());
        foundProfile.setBankAccountBranch(updatedProfile.getBankAccountBranch());
        foundProfile.setBankAccountDigit(updatedProfile.getBankAccountDigit());
        foundProfile.setBankAccountNumber(updatedProfile.getBankAccountNumber());
        foundProfile.setContractorName(updatedProfile.getContractorName());
        foundProfile.setContractNotice(updatedProfile.getContractNotice());
        foundProfile.setContractNumber(updatedProfile.getContractNumber());
        foundProfile.setContractEstablished(updatedProfile.getContractEstablished());
        foundProfile.setContractStart(updatedProfile.getContractStart());
        foundProfile.setContractEnd(updatedProfile.getContractEnd());
    }


}