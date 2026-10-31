package tn.esprit.autoloc.mapper;

import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.EmployeRequest;
import tn.esprit.autoloc.dto.EmployeResponse;

/** Mapper entre l'entité {@link Employe} et ses DTO. */
public final class EmployeMapper {

    private EmployeMapper() {
    }

    public static Employe toEntity(EmployeRequest request) {
        Employe employe = new Employe();
        employe.setNom(request.getNom());
        employe.setPrenom(request.getPrenom());
        employe.setRole(request.getRole());
        return employe;
    }

    public static EmployeResponse toResponse(Employe employe) {
        return EmployeResponse.builder()
                .idEmploye(employe.getIdEmploye())
                .nom(employe.getNom())
                .prenom(employe.getPrenom())
                .role(employe.getRole())
                .build();
    }
}
