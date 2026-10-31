package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.PaiementRequest;
import tn.esprit.autoloc.dto.PaiementResponse;
import java.util.List;

public interface IPaiementService {
    List<PaiementResponse> retrieveAllPaiements();
    PaiementResponse addPaiement(PaiementRequest p);
    PaiementResponse updatePaiement(Long idPaiement, PaiementRequest p);
    PaiementResponse retrievePaiement(Long idPaiement);
    void removePaiement(Long idPaiement);
    List<PaiementResponse> addPaiements(List<PaiementRequest> paiements);
}
