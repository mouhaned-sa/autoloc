package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.dto.ContratRequest;
import tn.esprit.autoloc.dto.ContratResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.ContratRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final ContratRepository contratRepository;

    @Override
    public List<ContratResponse> retrieveAllContrats() {
        log.info("Récupération de tous les contrats");
        return contratRepository.findAll().stream()
                .map(ContratMapper::toResponse)
                .toList();
    }

    @Override
    public ContratResponse addContrat(ContratRequest c) {
        log.info("Ajout d'un nouveau contrat du {}", c.getDateSignature());
        Contrat saved = contratRepository.save(ContratMapper.toEntity(c));
        log.info("Contrat créé avec l'id {}", saved.getIdContrat());
        return ContratMapper.toResponse(saved);
    }

    @Override
    public ContratResponse updateContrat(Long idContrat, ContratRequest c) {
        log.info("Mise à jour du contrat {}", idContrat);
        Contrat existing = retrieveContratEntity(idContrat);
        existing.setDateSignature(c.getDateSignature());
        existing.setMontantTotal(c.getMontantTotal());
        existing.setValide(c.getValide());
        Contrat updated = contratRepository.save(existing);
        log.info("Contrat {} mis à jour", idContrat);
        return ContratMapper.toResponse(updated);
    }

    @Override
    public ContratResponse retrieveContrat(Long idContrat) {
        return ContratMapper.toResponse(retrieveContratEntity(idContrat));
    }

    @Override
    public void removeContrat(Long idContrat) {
        checkExistence(idContrat);
        contratRepository.deleteById(idContrat);
        log.info("Contrat {} supprimé", idContrat);
    }

    @Override
    public List<ContratResponse> addContrats(List<ContratRequest> contrats) {
        log.info("Ajout de {} contrats", contrats.size());
        return contratRepository.saveAll(contrats.stream().map(ContratMapper::toEntity).toList()).stream()
                .map(ContratMapper::toResponse)
                .toList();
    }

    private Contrat retrieveContratEntity(Long idContrat) {
        return contratRepository.findById(idContrat)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", idContrat));
    }

    private void checkExistence(Long idContrat) {
        if (!contratRepository.existsById(idContrat)) {
            throw new ResourceNotFoundException("Contrat", idContrat);
        }
    }
}
