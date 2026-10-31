package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.VehiculeRequest;
import tn.esprit.autoloc.dto.VehiculeResponse;
import java.util.List;

public interface IVehiculeService {
    List<VehiculeResponse> retrieveAllVehicules();
    VehiculeResponse addVehicule(VehiculeRequest v);
    VehiculeResponse updateVehicule(Long idVehicule, VehiculeRequest v);
    VehiculeResponse retrieveVehicule(Long idVehicule);
    void removeVehicule(Long idVehicule);
    List<VehiculeResponse> addVehicules(List<VehiculeRequest> vehicules);
}
