package com.felipegandra.app_fluxusapiv2.modules.profiles;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.profiles.dtos.LogoViewModel;
import com.felipegandra.app_fluxusapiv2.modules.profiles.dtos.ProfileToPrintModel;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public Profile findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Profile", id));
    }

    public String getLogoBase64() throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get("src/main/resources/static/logo.png"));
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public void setLogoBase64(LogoViewModel model) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(model.base64Image());
        Files.write(Paths.get("src/main/resources/static/logo.png"), bytes);
    }

    public ProfileToPrintModel findToPrint() {
        List<Object[]> result = repository.findToPrintRaw();
        if (result.isEmpty()) {
            throw new NotFoundException("Profile", 1L);
        }

        Object[] row = result.getFirst();

        var profileToPrint = new ProfileToPrintModel();
        profileToPrint.setCnpj((String) row[0]);
        profileToPrint.setCompanyName((String) row[1]);
        profileToPrint.setContractNotice((String) row[2]);
        profileToPrint.setContractNumber((String) row[3]);

        return profileToPrint;
    }

    public String findTradingName() {
        return repository.findTradingName().orElseThrow(() -> new NotFoundException("Profile", 1L));
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