package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.EmployeRequest;
import tn.esprit.autoloc.dto.EmployeResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EmployeMapper;
import tn.esprit.autoloc.repository.EmployeRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final EmployeRepository employeRepository;

    @Override
    public List<EmployeResponse> retrieveAllEmployes() {
        log.info("Récupération de tous les employés");
        return employeRepository.findAll().stream()
                .map(EmployeMapper::toResponse)
                .toList();
    }

    @Override
    public EmployeResponse addEmploye(EmployeRequest e) {
        log.info("Ajout d'un nouvel employé : {} {}", e.getPrenom(), e.getNom());
        Employe saved = employeRepository.save(EmployeMapper.toEntity(e));
        log.info("Employé créé avec l'id {}", saved.getIdEmploye());
        return EmployeMapper.toResponse(saved);
    }

    @Override
    public EmployeResponse updateEmploye(Long idEmploye, EmployeRequest e) {
        log.info("Mise à jour de l'employé {}", idEmploye);
        Employe existing = retrieveEmployeEntity(idEmploye);
        existing.setNom(e.getNom());
        existing.setPrenom(e.getPrenom());
        existing.setRole(e.getRole());
        Employe updated = employeRepository.save(existing);
        log.info("Employé {} mis à jour", idEmploye);
        return EmployeMapper.toResponse(updated);
    }

    @Override
    public EmployeResponse retrieveEmploye(Long idEmploye) {
        return EmployeMapper.toResponse(retrieveEmployeEntity(idEmploye));
    }

    @Override
    public void removeEmploye(Long idEmploye) {
        checkExistence(idEmploye);
        employeRepository.deleteById(idEmploye);
        log.info("Employé {} supprimé", idEmploye);
    }

    @Override
    public List<EmployeResponse> addEmployes(List<EmployeRequest> employes) {
        log.info("Ajout de {} employés", employes.size());
        return employeRepository.saveAll(employes.stream().map(EmployeMapper::toEntity).toList()).stream()
                .map(EmployeMapper::toResponse)
                .toList();
    }

    private Employe retrieveEmployeEntity(Long idEmploye) {
        return employeRepository.findById(idEmploye)
                .orElseThrow(() -> new ResourceNotFoundException("Employé", idEmploye));
    }

    private void checkExistence(Long idEmploye) {
        if (!employeRepository.existsById(idEmploye)) {
            throw new ResourceNotFoundException("Employé", idEmploye);
        }
    }
}
