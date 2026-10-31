package tn.esprit.autoloc.mapper;

import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceRequest;
import tn.esprit.autoloc.dto.AgenceResponse;

/** Mapper entre l'entité {@link Agence} et ses DTO. */
public final class AgenceMapper {

    private AgenceMapper() {
    }

    public static Agence toEntity(AgenceRequest request) {
        Agence agence = new Agence();
        agence.setNom(request.getNom());
        agence.setVille(request.getVille());
        agence.setAdresse(request.getAdresse());
        agence.setTelephone(request.getTelephone());
        return agence;
    }

    public static AgenceResponse toResponse(Agence agence) {
        return AgenceResponse.builder()
                .idAgence(agence.getIdAgence())
                .nom(agence.getNom())
                .ville(agence.getVille())
                .adresse(agence.getAdresse())
                .telephone(agence.getTelephone())
                .build();
    }
}
