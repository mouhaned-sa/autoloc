package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementRequest;
import tn.esprit.autoloc.dto.PaiementResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.PaiementMapper;
import tn.esprit.autoloc.repository.PaiementRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final PaiementRepository paiementRepository;

    @Override
    public List<PaiementResponse> retrieveAllPaiements() {
        log.info("Récupération de tous les paiements");
        return paiementRepository.findAll().stream()
                .map(PaiementMapper::toResponse)
                .toList();
    }

    @Override
    public PaiementResponse addPaiement(PaiementRequest p) {
        log.info("Ajout d'un nouveau paiement de {} ", p.getMontant());
        Paiement saved = paiementRepository.save(PaiementMapper.toEntity(p));
        log.info("Paiement créé avec l'id {}", saved.getIdPaiement());
        return PaiementMapper.toResponse(saved);
    }

    @Override
    public PaiementResponse updatePaiement(Long idPaiement, PaiementRequest p) {
        log.info("Mise à jour du paiement {}", idPaiement);
        Paiement existing = retrievePaiementEntity(idPaiement);
        existing.setMontant(p.getMontant());
        existing.setDatePaiement(p.getDatePaiement());
        existing.setModePaiement(p.getModePaiement());
        Paiement updated = paiementRepository.save(existing);
        log.info("Paiement {} mis à jour", idPaiement);
        return PaiementMapper.toResponse(updated);
    }

    @Override
    public PaiementResponse retrievePaiement(Long idPaiement) {
        return PaiementMapper.toResponse(retrievePaiementEntity(idPaiement));
    }

    @Override
    public void removePaiement(Long idPaiement) {
        checkExistence(idPaiement);
        paiementRepository.deleteById(idPaiement);
        log.info("Paiement {} supprimé", idPaiement);
    }

    @Override
    public List<PaiementResponse> addPaiements(List<PaiementRequest> paiements) {
        log.info("Ajout de {} paiements", paiements.size());
        return paiementRepository.saveAll(paiements.stream().map(PaiementMapper::toEntity).toList()).stream()
                .map(PaiementMapper::toResponse)
                .toList();
    }

    private Paiement retrievePaiementEntity(Long idPaiement) {
        return paiementRepository.findById(idPaiement)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", idPaiement));
    }

    private void checkExistence(Long idPaiement) {
        if (!paiementRepository.existsById(idPaiement)) {
            throw new ResourceNotFoundException("Paiement", idPaiement);
        }
    }
}
