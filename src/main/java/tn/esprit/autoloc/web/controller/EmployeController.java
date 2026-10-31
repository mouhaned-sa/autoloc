package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.EmployeRequest;
import tn.esprit.autoloc.dto.EmployeResponse;
import tn.esprit.autoloc.service.IEmployeService;
import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @GetMapping
    public List<EmployeResponse> retrieveAllEmployes() {
        return employeService.retrieveAllEmployes();
    }

    @PostMapping
    public EmployeResponse addEmploye(@Valid @RequestBody EmployeRequest e) {
        return employeService.addEmploye(e);
    }

    @PutMapping("/{id}")
    public EmployeResponse updateEmploye(@PathVariable @Positive Long id,
                                         @Valid @RequestBody EmployeRequest e) {
        return employeService.updateEmploye(id, e);
    }

    @GetMapping("/{id}")
    public EmployeResponse retrieveEmploye(@PathVariable @Positive Long id) {
        return employeService.retrieveEmploye(id);
    }

    @DeleteMapping("/{id}")
    public void removeEmploye(@PathVariable @Positive Long id) {
        employeService.removeEmploye(id);
    }
}
