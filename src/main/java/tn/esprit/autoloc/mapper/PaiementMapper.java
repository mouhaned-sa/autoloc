package tn.esprit.autoloc.mapper;

import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementRequest;
import tn.esprit.autoloc.dto.PaiementResponse;

/** Mapper entre l'entité {@link Paiement} et ses DTO. */
public final class PaiementMapper {

    private PaiementMapper() {
    }

    public static Paiement toEntity(PaiementRequest request) {
        Paiement paiement = new Paiement();
        paiement.setMontant(request.getMontant());
        paiement.setDatePaiement(request.getDatePaiement());
        paiement.setModePaiement(request.getModePaiement());
        return paiement;
    }

    public static PaiementResponse toResponse(Paiement paiement) {
        return PaiementResponse.builder()
                .idPaiement(paiement.getIdPaiement())
                .montant(paiement.getMontant())
                .datePaiement(paiement.getDatePaiement())
                .modePaiement(paiement.getModePaiement())
                .build();
    }
}
