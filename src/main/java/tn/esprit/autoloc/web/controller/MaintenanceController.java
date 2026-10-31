package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.MaintenanceRequest;
import tn.esprit.autoloc.dto.MaintenanceResponse;
import tn.esprit.autoloc.service.IMaintenanceService;
import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @GetMapping
    public List<MaintenanceResponse> retrieveAllMaintenances() {
        return maintenanceService.retrieveAllMaintenances();
    }

    @PostMapping
    public MaintenanceResponse addMaintenance(@Valid @RequestBody MaintenanceRequest m) {
        return maintenanceService.addMaintenance(m);
    }

    @PutMapping("/{id}")
    public MaintenanceResponse updateMaintenance(@PathVariable @Positive Long id,
                                                 @Valid @RequestBody MaintenanceRequest m) {
        return maintenanceService.updateMaintenance(id, m);
    }

    @GetMapping("/{id}")
    public MaintenanceResponse retrieveMaintenance(@PathVariable @Positive Long id) {
        return maintenanceService.retrieveMaintenance(id);
    }

    @DeleteMapping("/{id}")
    public void removeMaintenance(@PathVariable @Positive Long id) {
        maintenanceService.removeMaintenance(id);
    }
}
