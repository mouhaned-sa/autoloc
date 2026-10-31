package tn.esprit.autoloc.mapper;

import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.MaintenanceRequest;
import tn.esprit.autoloc.dto.MaintenanceResponse;

/** Mapper entre l'entité {@link Maintenance} et ses DTO. */
public final class MaintenanceMapper {

    private MaintenanceMapper() {
    }

    public static Maintenance toEntity(MaintenanceRequest request) {
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(request.getDateDebut());
        maintenance.setDateFin(request.getDateFin());
        maintenance.setDescription(request.getDescription());
        return maintenance;
    }

    public static MaintenanceResponse toResponse(Maintenance maintenance) {
        return MaintenanceResponse.builder()
                .idMaintenance(maintenance.getIdMaintenance())
                .dateDebut(maintenance.getDateDebut())
                .dateFin(maintenance.getDateFin())
                .description(maintenance.getDescription())
                .build();
    }
}
