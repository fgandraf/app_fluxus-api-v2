package com.felipegandra.app_fluxusapiv2.modules.profiles;

import com.felipegandra.app_fluxusapiv2.exceptions.DatabaseOperationException;
import com.felipegandra.app_fluxusapiv2.exceptions.ProfileLogoNotFoundException;
import com.felipegandra.app_fluxusapiv2.exceptions.ProfileLogoNotWrittenException;
import com.felipegandra.app_fluxusapiv2.exceptions.ProfileNotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.profiles.dtos.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ProfileService {
    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public ProfileResponse findById(Long id) {
        var profile = repository.findById(id).orElseThrow(() -> new ProfileNotFoundException(id));
        return new ProfileResponse(profile);
    }

    public ProfileLogoResponse getLogoBase64() {
        try{
            var fileContent = Files.readAllBytes(Paths.get("src/main/resources/static/logo.png"));
            var encoded = Base64.getEncoder().encodeToString(fileContent);
            return new ProfileLogoResponse(encoded);
        }catch (IOException e){
            throw new ProfileLogoNotFoundException();
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public void updateProfileLogo(ProfileUpdateLogoRequest request) {
        try{
            var fileContent = Base64.getDecoder().decode(request.base64Image());
            Files.write(Paths.get("src/main/resources/static/logo.png"), fileContent);
        }catch (IOException e){
            throw new ProfileLogoNotWrittenException();
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public ProfileToPrintResponse getToPrint() {
        try{
            var result = repository.findToPrint();
            if (result.isEmpty()) {
                throw new ProfileNotFoundException(1L);
            }

            return new ProfileToPrintResponse(
                    (String) result.getFirst()[0],
                    (String) result.getFirst()[1],
                    (String) result.getFirst()[2],
                    (String) result.getFirst()[3],
                    (String) result.getFirst()[4]
            );
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public ProfileTradingNameResponse findTradingName() {
        var tradingName = repository.findTradingName().orElseThrow(() -> new ProfileNotFoundException(1L));
        return new ProfileTradingNameResponse(tradingName);
    }

    public ProfileResponse create(ProfileCreateRequest request) {

        try{
            var profile = new Profile(
                    1L,
                    request.cnpj(),
                    request.tradingName(),
                    request.companyName(),
                    request.stateId(),
                    request.cityId(),
                    request.address(),
                    request.complement(),
                    request.district(),
                    request.city(),
                    request.zip(),
                    request.state(),
                    request.establishmentDate(),
                    request.phone1(),
                    request.phone2(),
                    request.email(),
                    request.bankAccountName(),
                    request.bankAccountType(),
                    request.bankAccountBranch(),
                    request.bankAccountDigit(),
                    request.bankAccountNumber(),
                    request.contractorName(),
                    request.contractNotice(),
                    request.contractNumber(),
                    request.contractEstablished(),
                    request.contractStart(),
                    request.contractEnd()
            );

            var savedProfile = repository.save(profile);
            return new ProfileResponse(savedProfile);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public ProfileResponse update(ProfileUpdateRequest request){
        var profile = repository.findById(request.id()).orElseThrow(() -> new ProfileNotFoundException(request.id()));

        try{
                profile.setCnpj(request.cnpj());
                profile.setTradingName(request.tradingName());
                profile.setCompanyName(request.companyName());
                profile.setStateId(request.stateId());
                profile.setCityId(request.cityId());
                profile.setAddress(request.address());
                profile.setComplement(request.complement());
                profile.setDistrict(request.district());
                profile.setCity(request.city());
                profile.setZip(request.zip());
                profile.setState(request.state());
                profile.setEstablishmentDate(request.establishmentDate());
                profile.setPhone1(request.phone1());
                profile.setPhone2(request.phone2());
                profile.setEmail(request.email());
                profile.setBankAccountName(request.bankAccountName());
                profile.setBankAccountType(request.bankAccountType());
                profile.setBankAccountBranch(request.bankAccountBranch());
                profile.setBankAccountDigit(request.bankAccountDigit());
                profile.setBankAccountNumber(request.bankAccountNumber());
                profile.setContractorName(request.contractorName());
                profile.setContractNotice(request.contractNotice());
                profile.setContractNumber(request.contractNumber());
                profile.setContractEstablished(request.contractEstablished());
                profile.setContractStart(request.contractStart());
                profile.setContractEnd(request.contractEnd());

                var savedProfile = repository.save(profile);
                return new ProfileResponse(savedProfile);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }
}