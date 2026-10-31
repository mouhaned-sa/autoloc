package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.EquipementRequest;
import tn.esprit.autoloc.dto.EquipementResponse;
import tn.esprit.autoloc.service.IEquipementService;
import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    @GetMapping
    public List<EquipementResponse> retrieveAllEquipements() {
        return equipementService.retrieveAllEquipements();
    }

    @PostMapping
    public EquipementResponse addEquipement(@Valid @RequestBody EquipementRequest e) {
        return equipementService.addEquipement(e);
    }

    @PutMapping("/{id}")
    public EquipementResponse updateEquipement(@PathVariable @Positive Long id,
                                               @Valid @RequestBody EquipementRequest e) {
        return equipementService.updateEquipement(id, e);
    }

    @GetMapping("/{id}")
    public EquipementResponse retrieveEquipement(@PathVariable @Positive Long id) {
        return equipementService.retrieveEquipement(id);
    }

    @DeleteMapping("/{id}")
    public void removeEquipement(@PathVariable @Positive Long id) {
        equipementService.removeEquipement(id);
    }
}
