package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.EquipementRequest;
import tn.esprit.autoloc.dto.EquipementResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.repository.EquipementRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final EquipementRepository equipementRepository;

    @Override
    public List<EquipementResponse> retrieveAllEquipements() {
        log.info("Récupération de tous les équipements");
        return equipementRepository.findAll().stream()
                .map(EquipementMapper::toResponse)
                .toList();
    }

    @Override
    public EquipementResponse addEquipement(EquipementRequest e) {
        log.info("Ajout d'un nouvel équipement : {}", e.getLibelle());
        Equipement saved = equipementRepository.save(EquipementMapper.toEntity(e));
        log.info("Équipement créé avec l'id {}", saved.getIdEquipement());
        return EquipementMapper.toResponse(saved);
    }

    @Override
    public EquipementResponse updateEquipement(Long idEquipement, EquipementRequest e) {
        log.info("Mise à jour de l'équipement {}", idEquipement);
        Equipement existing = retrieveEquipementEntity(idEquipement);
        existing.setLibelle(e.getLibelle());
        Equipement updated = equipementRepository.save(existing);
        log.info("Équipement {} mis à jour", idEquipement);
        return EquipementMapper.toResponse(updated);
    }

    @Override
    public EquipementResponse retrieveEquipement(Long idEquipement) {
        return EquipementMapper.toResponse(retrieveEquipementEntity(idEquipement));
    }

    @Override
    public void removeEquipement(Long idEquipement) {
        checkExistence(idEquipement);
        equipementRepository.deleteById(idEquipement);
        log.info("Équipement {} supprimé", idEquipement);
    }

    @Override
    public List<EquipementResponse> addEquipements(List<EquipementRequest> equipements) {
        log.info("Ajout de {} équipements", equipements.size());
        return equipementRepository.saveAll(equipements.stream().map(EquipementMapper::toEntity).toList()).stream()
                .map(EquipementMapper::toResponse)
                .toList();
    }

    private Equipement retrieveEquipementEntity(Long idEquipement) {
        return equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Équipement", idEquipement));
    }

    private void checkExistence(Long idEquipement) {
        if (!equipementRepository.existsById(idEquipement)) {
            throw new ResourceNotFoundException("Équipement", idEquipement);
        }
    }
}
