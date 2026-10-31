package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.MaintenanceRequest;
import tn.esprit.autoloc.dto.MaintenanceResponse;
import java.util.List;

public interface IMaintenanceService {
    List<MaintenanceResponse> retrieveAllMaintenances();
    MaintenanceResponse addMaintenance(MaintenanceRequest m);
    MaintenanceResponse updateMaintenance(Long idMaintenance, MaintenanceRequest m);
    MaintenanceResponse retrieveMaintenance(Long idMaintenance);
    void removeMaintenance(Long idMaintenance);
    List<MaintenanceResponse> addMaintenances(List<MaintenanceRequest> maintenances);
}
