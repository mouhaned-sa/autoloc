package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/** DTO d'entrée (création / mise à jour) d'un contrat. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratRequest {

    @NotNull(message = "La date de signature est obligatoire")
    private LocalDate dateSignature;

    @NotNull(message = "Le montant total est obligatoire")
    @PositiveOrZero(message = "Le montant total doit être positif ou nul")
    @Digits(integer = 8, fraction = 2, message = "Le montant total doit être un montant valide (10 chiffres max, 2 décimales)")
    private BigDecimal montantTotal;

    @NotNull(message = "Le statut de validation est obligatoire (true ou false)")
    private Boolean valide;
}
