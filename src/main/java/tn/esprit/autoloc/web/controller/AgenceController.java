package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.AgenceRequest;
import tn.esprit.autoloc.dto.AgenceResponse;
import tn.esprit.autoloc.service.IAgenceService;
import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agence", description = "CRUD des agences AutoLoc")
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Lister toutes les agences", description = "Retourne la liste complète des agences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    @GetMapping
    public List<AgenceResponse> retrieveAllAgences() {
        return agenceService.retrieveAllAgences();
    }

    @Operation(summary = "Ajouter une agence", description = "Crée une nouvelle agence à partir du JSON envoyé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agence créée"),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide")
    })
    @PostMapping
    public AgenceResponse addAgence(@Valid @RequestBody AgenceRequest a) {
        return agenceService.addAgence(a);
    }

    @Operation(summary = "Mettre à jour une agence", description = "Met à jour l'agence dont l'id est passé dans le chemin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agence mise à jour"),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide"),
            @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @PutMapping("/{id}")
    public AgenceResponse updateAgence(@PathVariable @Positive Long id,
                                       @Valid @RequestBody AgenceRequest a) {
        return agenceService.updateAgence(id, a);
    }

    @Operation(summary = "Consulter une agence", description = "Retourne l'agence correspondant à l'id envoyé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agence trouvée"),
            @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @GetMapping("/{id}")
    public AgenceResponse retrieveAgence(@PathVariable @Positive Long id) {
        return agenceService.retrieveAgence(id);
    }

    @Operation(summary = "Supprimer une agence", description = "Supprime l'agence correspondant à l'id envoyé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agence supprimée"),
            @ApiResponse(responseCode = "404", description = "Agence non trouvée")
    })
    @DeleteMapping("/{id}")
    public void removeAgence(@PathVariable @Positive Long id) {
        agenceService.removeAgence(id);
    }
}
