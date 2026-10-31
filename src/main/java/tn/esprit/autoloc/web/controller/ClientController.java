package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.ClientRequest;
import tn.esprit.autoloc.dto.ClientResponse;
import tn.esprit.autoloc.service.IClientService;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Client", description = "CRUD des clients de l'agence")
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Lister tous les clients", description = "Retourne la liste complète des clients enregistrés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    @GetMapping
    public List<ClientResponse> retrieveAllClients() {
        return clientService.retrieveAllClients();
    }

    @Operation(summary = "Ajouter un client", description = "Crée un nouveau client à partir du JSON envoyé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client créé"),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide")
    })
    @PostMapping
    public ClientResponse addClient(@Valid @RequestBody ClientRequest c) {
        return clientService.addClient(c);
    }

    @Operation(summary = "Mettre à jour un client", description = "Met à jour le client dont l'id est passé dans le chemin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client mis à jour"),
            @ApiResponse(responseCode = "400", description = "Corps de requête invalide"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @PutMapping("/{id}")
    public ClientResponse updateClient(@PathVariable @Positive Long id,
                                       @Valid @RequestBody ClientRequest c) {
        return clientService.updateClient(id, c);
    }

    @Operation(summary = "Consulter un client", description = "Retourne le client correspondant à l'id envoyé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client trouvé"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @GetMapping("/{id}")
    public ClientResponse retrieveClient(@PathVariable @Positive Long id) {
        return clientService.retrieveClient(id);
    }

    @Operation(summary = "Supprimer un client", description = "Supprime le client correspondant à l'id envoyé")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client supprimé"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @DeleteMapping("/{id}")
    public void removeClient(@PathVariable @Positive Long id) {
        clientService.removeClient(id);
    }
}
