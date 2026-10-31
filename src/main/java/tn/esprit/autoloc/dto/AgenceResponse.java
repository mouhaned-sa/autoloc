package tn.esprit.autoloc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** DTO de sortie d'une agence renvoyé par tous les endpoints. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgenceResponse {

    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;
}
