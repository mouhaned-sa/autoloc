package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/** DTO d'entrée (création / mise à jour) d'une maintenance. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequest {

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    /** Contrôle de saisie croisé : la date de fin doit être postérieure à la date de début. */
    @AssertTrue(message = "La date de fin doit être postérieure à la date de début")
    public boolean isPeriodeValide() {
        if (dateFin == null || dateDebut == null) {
            return true;
        }
        return dateFin.isAfter(dateDebut);
    }
}
