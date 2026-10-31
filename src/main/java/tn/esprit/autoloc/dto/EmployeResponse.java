package tn.esprit.autoloc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.RoleEmploye;

/** DTO de sortie d'un employé renvoyé par tous les endpoints. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeResponse {

    private Long idEmploye;
    private String nom;
    private String prenom;
    private RoleEmploye role;
}
