package com.felipegandra.app_fluxusapiv2.modules.professionals;

import com.felipegandra.app_fluxusapiv2.exceptions.DatabaseOperationException;
import com.felipegandra.app_fluxusapiv2.exceptions.ProfessionalNotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.professionals.dtos.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessionalService {

    private final ProfessionalRepository repository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.repository = professionalRepository;
    }

    public List<ProfessionalIndexResponse> getProfessionalIndex() {
        List<ProfessionalIndexResponse> professionals = new ArrayList<>();

        try{
            repository.findProfessionalIndex().forEach(x -> professionals.add(objetoToIndexResponse(x)));
            return professionals;

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }

    }

    public List<ProfessionalTagNameIdResponse> getTagNameId() {

        List<ProfessionalTagNameIdResponse> professionals = new ArrayList<>();

        try{
            repository.findProfessionalIndex()
                    .forEach(x -> {
                        var result = objetoToIndexResponse(x);

                        var nameSplited = result.name().split(" ");
                        var firstName = nameSplited[0];
                        var lastName = nameSplited.length > 1 ? " " + nameSplited[nameSplited.length - 1] : "";
                        var profesionAbbreviation = result.profession() != null ? result.profession().substring(0, 3) + ". " : "";
                        var nameId = profesionAbbreviation + firstName + lastName;

                        var professional = new ProfessionalTagNameIdResponse(result.id(), result.tag(), nameId);

                        professionals.add(professional);
                    });

            return professionals;

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }

    }

    public ProfessionalResponse findById(Long id) {
        var professional = repository.findById(id).orElseThrow(() -> new ProfessionalNotFoundException(id));
        return new ProfessionalResponse(professional);
    }

    public ProfessionalResponse create(ProfessionalCreateRequest request) {

        try{
            var profesional = new Professional(
                    null,
                    request.tag(),
                    request.name(),
                    request.cpf(),
                    request.birthday(),
                    request.profession(),
                    request.permitNumber(),
                    request.association(),
                    request.phone1(),
                    request.phone2(),
                    request.email()
            );

            var savedProfessional = repository.save(profesional);
            return new ProfessionalResponse(savedProfessional);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public ProfessionalResponse update(ProfessionalUpdateRequest request) {
        var professional = repository.findById(request.id()).orElseThrow(() -> new ProfessionalNotFoundException(request.id()));

        try{
            professional.setTag(request.tag());
            professional.setName(request.name());
            professional.setCpf(request.cpf());
            professional.setBirthday(request.birthday());
            professional.setProfession(request.profession());
            professional.setPermitNumber(request.permitNumber());
            professional.setAssociation(request.association());
            professional.setPhone1(request.phone1());
            professional.setPhone2(request.phone2());
            professional.setEmail(request.email());

            var savedProfessional = repository.save(professional);
            return new ProfessionalResponse(savedProfessional);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public void delete(Long id) {
        var professional = repository.findById(id).orElseThrow(() -> new ProfessionalNotFoundException(id));

        try {
            repository.delete(professional);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }
    }

    private ProfessionalIndexResponse objetoToIndexResponse(Object[] result){
        var id = (Long) result[0];
        var tag = (String) result[1];
        var name = (String) result[2];
        var profession = (String) result[3];
        var phone1 = (String) result[4];

        return new ProfessionalIndexResponse(id, tag, name, profession, phone1);
    }

}