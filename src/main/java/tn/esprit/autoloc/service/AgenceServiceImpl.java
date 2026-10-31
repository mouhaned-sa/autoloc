package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceRequest;
import tn.esprit.autoloc.dto.AgenceResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.AgenceMapper;
import tn.esprit.autoloc.repository.AgenceRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final AgenceRepository agenceRepository;

    @Override
    public List<AgenceResponse> retrieveAllAgences() {
        log.info("Récupération de toutes les agences");
        return agenceRepository.findAll().stream()
                .map(AgenceMapper::toResponse)
                .toList();
    }

    @Override
    public AgenceResponse addAgence(AgenceRequest a) {
        log.info("Ajout d'une nouvelle agence : {}", a.getNom());
        Agence saved = agenceRepository.save(AgenceMapper.toEntity(a));
        log.info("Agence créée avec l'id {}", saved.getIdAgence());
        return AgenceMapper.toResponse(saved);
    }

    @Override
    public AgenceResponse updateAgence(Long idAgence, AgenceRequest a) {
        log.info("Mise à jour de l'agence {}", idAgence);
        Agence existing = retrieveAgenceEntity(idAgence);
        existing.setNom(a.getNom());
        existing.setVille(a.getVille());
        existing.setAdresse(a.getAdresse());
        existing.setTelephone(a.getTelephone());
        Agence updated = agenceRepository.save(existing);
        log.info("Agence {} mise à jour", idAgence);
        return AgenceMapper.toResponse(updated);
    }

    @Override
    public AgenceResponse retrieveAgence(Long idAgence) {
        return AgenceMapper.toResponse(retrieveAgenceEntity(idAgence));
    }

    @Override
    public void removeAgence(Long idAgence) {
        checkExistence(idAgence);
        agenceRepository.deleteById(idAgence);
        log.info("Agence {} supprimée", idAgence);
    }

    @Override
    public List<AgenceResponse> addAgences(List<AgenceRequest> agences) {
        log.info("Ajout de {} agences", agences.size());
        return agenceRepository.saveAll(agences.stream().map(AgenceMapper::toEntity).toList()).stream()
                .map(AgenceMapper::toResponse)
                .toList();
    }

    private Agence retrieveAgenceEntity(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }

    private void checkExistence(Long idAgence) {
        if (!agenceRepository.existsById(idAgence)) {
            throw new ResourceNotFoundException("Agence", idAgence);
        }
    }
}
