package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.ContratRequest;
import tn.esprit.autoloc.dto.ContratResponse;
import tn.esprit.autoloc.service.IContratService;
import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @GetMapping
    public List<ContratResponse> retrieveAllContrats() {
        return contratService.retrieveAllContrats();
    }

    @PostMapping
    public ContratResponse addContrat(@Valid @RequestBody ContratRequest c) {
        return contratService.addContrat(c);
    }

    @PutMapping("/{id}")
    public ContratResponse updateContrat(@PathVariable @Positive Long id,
                                         @Valid @RequestBody ContratRequest c) {
        return contratService.updateContrat(id, c);
    }

    @GetMapping("/{id}")
    public ContratResponse retrieveContrat(@PathVariable @Positive Long id) {
        return contratService.retrieveContrat(id);
    }

    @DeleteMapping("/{id}")
    public void removeContrat(@PathVariable @Positive Long id) {
        contratService.removeContrat(id);
    }
}
