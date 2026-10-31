package tn.esprit.autoloc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/** DTO de sortie d'un contrat renvoyé par tous les endpoints. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratResponse {

    private Long idContrat;
    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private Boolean valide;
}
