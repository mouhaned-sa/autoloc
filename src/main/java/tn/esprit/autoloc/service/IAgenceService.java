package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.AgenceRequest;
import tn.esprit.autoloc.dto.AgenceResponse;
import java.util.List;

public interface IAgenceService {
    List<AgenceResponse> retrieveAllAgences();
    AgenceResponse addAgence(AgenceRequest a);
    AgenceResponse updateAgence(Long idAgence, AgenceRequest a);
    AgenceResponse retrieveAgence(Long idAgence);
    void removeAgence(Long idAgence);
    List<AgenceResponse> addAgences(List<AgenceRequest> agences);
}
