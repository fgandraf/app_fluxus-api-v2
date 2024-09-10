package com.felipegandra.app_fluxusapiv2.modules.professionals;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.professionals.dtos.ProfessionalDetails;
import com.felipegandra.app_fluxusapiv2.modules.professionals.dtos.ProfessionalTagNameId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionalService {

    private final ProfessionalRepository repository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.repository = professionalRepository;
    }

    public List<ProfessionalDetails> getProfessionalIndex() {
        return repository.findProfessionalIndex()
                .stream()
                .map(result -> new ProfessionalDetails(
                        (Long) result[0],
                        (String) result[1],
                        (String) result[2],
                        (String) result[3],
                        (String) result[4]
                ))
                .collect(Collectors.toList());
    }

    public List<ProfessionalTagNameId> getTagNameId() {

        return repository.findProfessionalTagNameId()
                .stream()
                .map(result -> {
                    Long id = (Long) result[0];
                    String tag = (String) result[1];
                    String profession = (String) result[2];
                    String name = (String) result[3];

                    String nameId = profession != null ? profession.substring(0, 3) + ". " : "";
                    String[] nameParts = name.split(" ");
                    if (nameParts.length > 1) {
                        nameId += nameParts[0] + " " + nameParts[nameParts.length - 1];
                    } else {
                        nameId += name;
                    }

                    return new ProfessionalTagNameId(id, tag, nameId);
                    })
                .collect(Collectors.toList()
                );
    }

    public Professional findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Professional", id));
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