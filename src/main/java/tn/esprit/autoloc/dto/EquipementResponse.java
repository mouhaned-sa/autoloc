package tn.esprit.autoloc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** DTO de sortie d'un équipement renvoyé par tous les endpoints. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipementResponse {

    private Long idEquipement;
    private String libelle;
}
