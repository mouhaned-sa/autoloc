package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.MaintenanceRequest;
import tn.esprit.autoloc.dto.MaintenanceResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.repository.MaintenanceRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    @Override
    public List<MaintenanceResponse> retrieveAllMaintenances() {
        log.info("Récupération de toutes les maintenances");
        return maintenanceRepository.findAll().stream()
                .map(MaintenanceMapper::toResponse)
                .toList();
    }

    @Override
    public MaintenanceResponse addMaintenance(MaintenanceRequest m) {
        log.info("Ajout d'une nouvelle maintenance du {}", m.getDateDebut());
        Maintenance saved = maintenanceRepository.save(MaintenanceMapper.toEntity(m));
        log.info("Maintenance créée avec l'id {}", saved.getIdMaintenance());
        return MaintenanceMapper.toResponse(saved);
    }

    @Override
    public MaintenanceResponse updateMaintenance(Long idMaintenance, MaintenanceRequest m) {
        log.info("Mise à jour de la maintenance {}", idMaintenance);
        Maintenance existing = retrieveMaintenanceEntity(idMaintenance);
        existing.setDateDebut(m.getDateDebut());
        existing.setDateFin(m.getDateFin());
        existing.setDescription(m.getDescription());
        Maintenance updated = maintenanceRepository.save(existing);
        log.info("Maintenance {} mise à jour", idMaintenance);
        return MaintenanceMapper.toResponse(updated);
    }

    @Override
    public MaintenanceResponse retrieveMaintenance(Long idMaintenance) {
        return MaintenanceMapper.toResponse(retrieveMaintenanceEntity(idMaintenance));
    }

    @Override
    public void removeMaintenance(Long idMaintenance) {
        checkExistence(idMaintenance);
        maintenanceRepository.deleteById(idMaintenance);
        log.info("Maintenance {} supprimée", idMaintenance);
    }

    @Override
    public List<MaintenanceResponse> addMaintenances(List<MaintenanceRequest> maintenances) {
        log.info("Ajout de {} maintenances", maintenances.size());
        return maintenanceRepository.saveAll(maintenances.stream().map(MaintenanceMapper::toEntity).toList()).stream()
                .map(MaintenanceMapper::toResponse)
                .toList();
    }

    private Maintenance retrieveMaintenanceEntity(Long idMaintenance) {
        return maintenanceRepository.findById(idMaintenance)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", idMaintenance));
    }

    private void checkExistence(Long idMaintenance) {
        if (!maintenanceRepository.existsById(idMaintenance)) {
            throw new ResourceNotFoundException("Maintenance", idMaintenance);
        }
    }
}
