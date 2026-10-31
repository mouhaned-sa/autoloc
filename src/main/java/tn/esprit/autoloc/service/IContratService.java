package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.ContratRequest;
import tn.esprit.autoloc.dto.ContratResponse;
import java.util.List;

public interface IContratService {
    List<ContratResponse> retrieveAllContrats();
    ContratResponse addContrat(ContratRequest c);
    ContratResponse updateContrat(Long idContrat, ContratRequest c);
    ContratResponse retrieveContrat(Long idContrat);
    void removeContrat(Long idContrat);
    List<ContratResponse> addContrats(List<ContratRequest> contrats);
}
