package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/** DTO d'entrée (création / mise à jour) d'une réservation. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    /** Contrôle de saisie croisé : la date de fin doit être postérieure à la date de début. */
    @AssertTrue(message = "La date de fin doit être postérieure à la date de début")
    public boolean isPeriodeValide() {
        if (dateFin == null || dateDebut == null) {
            return true;
        }
        return dateFin.isAfter(dateDebut);
    }
}
