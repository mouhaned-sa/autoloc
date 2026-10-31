package tn.esprit.autoloc.mapper;

import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.ClientRequest;
import tn.esprit.autoloc.dto.ClientResponse;

/** Mapper entre l'entité {@link Client} et ses DTO. */
public final class ClientMapper {

    private ClientMapper() {
    }

    public static Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setNom(request.getNom());
        client.setPrenom(request.getPrenom());
        client.setEmail(request.getEmail());
        client.setTelephone(request.getTelephone());
        client.setNumPermis(request.getNumPermis());
        client.setDateInscription(request.getDateInscription());
        return client;
    }

    public static ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .idClient(client.getIdClient())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .email(client.getEmail())
                .telephone(client.getTelephone())
                .numPermis(client.getNumPermis())
                .dateInscription(client.getDateInscription())
                .build();
    }
}
