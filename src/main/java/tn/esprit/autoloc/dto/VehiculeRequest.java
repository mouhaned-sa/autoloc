package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

import java.math.BigDecimal;

/** DTO d'entrée (création / mise à jour) d'un véhicule. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehiculeRequest {

    @NotBlank(message = "L'immatriculation est obligatoire")
    @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
    private String immatriculation;

    @NotBlank(message = "La marque est obligatoire")
    @Size(max = 50, message = "La marque ne doit pas dépasser 50 caractères")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
    private String modele;

    @NotNull(message = "La catégorie est obligatoire (CITADINE, BERLINE, SUV ou UTILITAIRE)")
    private CategorieVehicule categorie;

    @NotNull(message = "Le tarif journalier est obligatoire")
    @Positive(message = "Le tarif journalier doit être strictement positif")
    @Digits(integer = 8, fraction = 2, message = "Le tarif doit être un montant valide (10 chiffres max, 2 décimales)")
    private BigDecimal tarifJournalier;

    @NotNull(message = "Le statut est obligatoire (DISPONIBLE, LOUE ou MAINTENANCE)")
    private StatutVehicule statut;
}
