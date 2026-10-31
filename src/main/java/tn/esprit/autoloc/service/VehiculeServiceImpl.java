package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeRequest;
import tn.esprit.autoloc.dto.VehiculeResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.VehiculeRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public List<VehiculeResponse> retrieveAllVehicules() {
        log.info("Récupération de tous les véhicules");
        return vehiculeRepository.findAll().stream()
                .map(VehiculeMapper::toResponse)
                .toList();
    }

    @Override
    public VehiculeResponse addVehicule(VehiculeRequest v) {
        log.info("Ajout d'un nouveau véhicule : {} {}", v.getMarque(), v.getModele());
        Vehicule saved = vehiculeRepository.save(VehiculeMapper.toEntity(v));
        log.info("Véhicule créé avec l'id {}", saved.getIdVehicule());
        return VehiculeMapper.toResponse(saved);
    }

    @Override
    public VehiculeResponse updateVehicule(Long idVehicule, VehiculeRequest v) {
        log.info("Mise à jour du véhicule {}", idVehicule);
        Vehicule existing = retrieveVehiculeEntity(idVehicule);
        existing.setImmatriculation(v.getImmatriculation());
        existing.setMarque(v.getMarque());
        existing.setModele(v.getModele());
        existing.setCategorie(v.getCategorie());
        existing.setTarifJournalier(v.getTarifJournalier());
        existing.setStatut(v.getStatut());
        Vehicule updated = vehiculeRepository.save(existing);
        log.info("Véhicule {} mis à jour", idVehicule);
        return VehiculeMapper.toResponse(updated);
    }

    @Override
    public VehiculeResponse retrieveVehicule(Long idVehicule) {
        return VehiculeMapper.toResponse(retrieveVehiculeEntity(idVehicule));
    }

    @Override
    public void removeVehicule(Long idVehicule) {
        checkExistence(idVehicule);
        vehiculeRepository.deleteById(idVehicule);
        log.info("Véhicule {} supprimé", idVehicule);
    }

    @Override
    public List<VehiculeResponse> addVehicules(List<VehiculeRequest> vehicules) {
        log.info("Ajout de {} véhicules", vehicules.size());
        return vehiculeRepository.saveAll(vehicules.stream().map(VehiculeMapper::toEntity).toList()).stream()
                .map(VehiculeMapper::toResponse)
                .toList();
    }

    private Vehicule retrieveVehiculeEntity(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", idVehicule));
    }

    private void checkExistence(Long idVehicule) {
        if (!vehiculeRepository.existsById(idVehicule)) {
            throw new ResourceNotFoundException("Véhicule", idVehicule);
        }
    }
}
