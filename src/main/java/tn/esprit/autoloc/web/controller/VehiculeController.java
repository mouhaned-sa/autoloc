package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.VehiculeRequest;
import tn.esprit.autoloc.dto.VehiculeResponse;
import tn.esprit.autoloc.service.IVehiculeService;
import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Vehicule", description = "CRUD des véhicules de la flotte")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister tous les véhicules", description = "Retourne la liste complète des véhicules de la flotte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    @GetMapping
    public List<VehiculeResponse> retrieveAllVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    @Operation(summary = "Ajouter un véhicule", description = "Crée un nouveau véhicule à partir du JSON envoyé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule créé"),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide")
    })
    @PostMapping
    public VehiculeResponse addVehicule(@Valid @RequestBody VehiculeRequest v) {
        return vehiculeService.addVehicule(v);
    }

    @Operation(summary = "Mettre à jour un véhicule", description = "Met à jour le véhicule dont l'id est passé dans le chemin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule mis à jour"),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide"),
            @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    })
    @PutMapping("/{id}")
    public VehiculeResponse updateVehicule(@PathVariable @Positive Long id,
                                           @Valid @RequestBody VehiculeRequest v) {
        return vehiculeService.updateVehicule(id, v);
    }

    @Operation(summary = "Consulter un véhicule", description = "Retourne le véhicule correspondant à l'id envoyé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule trouvé"),
            @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    })
    @GetMapping("/{id}")
    public VehiculeResponse retrieveVehicule(@PathVariable @Positive Long id) {
        return vehiculeService.retrieveVehicule(id);
    }

    @Operation(summary = "Supprimer un véhicule", description = "Supprime le véhicule correspondant à l'id envoyé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Véhicule supprimé"),
            @ApiResponse(responseCode = "404", description = "Véhicule non trouvé")
    })
    @DeleteMapping("/{id}")
    public void removeVehicule(@PathVariable @Positive Long id) {
        vehiculeService.removeVehicule(id);
    }
}
