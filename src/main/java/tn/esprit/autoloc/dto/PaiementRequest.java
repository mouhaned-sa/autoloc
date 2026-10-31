package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.ModePaiement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** DTO d'entrée (création / mise à jour) d'un paiement. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaiementRequest {

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être strictement positif")
    @Digits(integer = 8, fraction = 2, message = "Le montant doit être un montant valide (10 chiffres max, 2 décimales)")
    private BigDecimal montant;

    @NotNull(message = "La date de paiement est obligatoire")
    private LocalDateTime datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire (CARTE, ESPECES ou VIREMENT)")
    private ModePaiement modePaiement;
}
