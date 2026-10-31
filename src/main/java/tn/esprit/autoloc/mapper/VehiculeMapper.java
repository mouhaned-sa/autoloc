package tn.esprit.autoloc.mapper;

import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeRequest;
import tn.esprit.autoloc.dto.VehiculeResponse;

/** Mapper entre l'entité {@link Vehicule} et ses DTO. */
public final class VehiculeMapper {

    private VehiculeMapper() {
    }

    public static Vehicule toEntity(VehiculeRequest request) {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation(request.getImmatriculation());
        vehicule.setMarque(request.getMarque());
        vehicule.setModele(request.getModele());
        vehicule.setCategorie(request.getCategorie());
        vehicule.setTarifJournalier(request.getTarifJournalier());
        vehicule.setStatut(request.getStatut());
        return vehicule;
    }

    public static VehiculeResponse toResponse(Vehicule vehicule) {
        return VehiculeResponse.builder()
                .idVehicule(vehicule.getIdVehicule())
                .immatriculation(vehicule.getImmatriculation())
                .marque(vehicule.getMarque())
                .modele(vehicule.getModele())
                .categorie(vehicule.getCategorie())
                .tarifJournalier(vehicule.getTarifJournalier())
                .statut(vehicule.getStatut())
                .build();
    }
}
