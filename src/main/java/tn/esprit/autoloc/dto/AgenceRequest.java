package tn.esprit.autoloc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** DTO d'entrée (création / mise à jour) d'une agence. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgenceRequest {

    @NotBlank(message = "Le nom de l'agence est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    @NotBlank(message = "La ville est obligatoire")
    @Size(max = 100, message = "La ville ne doit pas dépasser 100 caractères")
    private String ville;

    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    private String adresse;

    @Size(max = 20, message = "Le téléphone ne doit pas dépasser 20 caractères")
    @Pattern(regexp = "^[+\\d\\s]*$", message = "Le téléphone ne doit contenir que des chiffres, espaces et +")
    private String telephone;
}
