package tn.esprit.autoloc.mapper;

import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.dto.ContratRequest;
import tn.esprit.autoloc.dto.ContratResponse;

/** Mapper entre l'entité {@link Contrat} et ses DTO. */
public final class ContratMapper {

    private ContratMapper() {
    }

    public static Contrat toEntity(ContratRequest request) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(request.getDateSignature());
        contrat.setMontantTotal(request.getMontantTotal());
        contrat.setValide(request.getValide());
        return contrat;
    }

    public static ContratResponse toResponse(Contrat contrat) {
        return ContratResponse.builder()
                .idContrat(contrat.getIdContrat())
                .dateSignature(contrat.getDateSignature())
                .montantTotal(contrat.getMontantTotal())
                .valide(contrat.getValide())
                .build();
    }
}
