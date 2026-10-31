package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.EquipementRequest;
import tn.esprit.autoloc.dto.EquipementResponse;
import java.util.List;

public interface IEquipementService {
    List<EquipementResponse> retrieveAllEquipements();
    EquipementResponse addEquipement(EquipementRequest e);
    EquipementResponse updateEquipement(Long idEquipement, EquipementRequest e);
    EquipementResponse retrieveEquipement(Long idEquipement);
    void removeEquipement(Long idEquipement);
    List<EquipementResponse> addEquipements(List<EquipementRequest> equipements);
}
