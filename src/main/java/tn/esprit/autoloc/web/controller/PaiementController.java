package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.PaiementRequest;
import tn.esprit.autoloc.dto.PaiementResponse;
import tn.esprit.autoloc.service.IPaiementService;
import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @GetMapping
    public List<PaiementResponse> retrieveAllPaiements() {
        return paiementService.retrieveAllPaiements();
    }

    @PostMapping
    public PaiementResponse addPaiement(@Valid @RequestBody PaiementRequest p) {
        return paiementService.addPaiement(p);
    }

    @PutMapping("/{id}")
    public PaiementResponse updatePaiement(@PathVariable @Positive Long id,
                                           @Valid @RequestBody PaiementRequest p) {
        return paiementService.updatePaiement(id, p);
    }

    @GetMapping("/{id}")
    public PaiementResponse retrievePaiement(@PathVariable @Positive Long id) {
        return paiementService.retrievePaiement(id);
    }

    @DeleteMapping("/{id}")
    public void removePaiement(@PathVariable @Positive Long id) {
        paiementService.removePaiement(id);
    }
}
