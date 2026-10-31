package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ReservationRequest;
import tn.esprit.autoloc.dto.ReservationResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.ReservationRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<ReservationResponse> retrieveAllReservations() {
        log.info("Récupération de toutes les réservations");
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }

    @Override
    public ReservationResponse addReservation(ReservationRequest r) {
        log.info("Ajout d'une nouvelle réservation du {} au {}", r.getDateDebut(), r.getDateFin());
        Reservation saved = reservationRepository.save(ReservationMapper.toEntity(r));
        log.info("Réservation créée avec l'id {}", saved.getIdReservation());
        return ReservationMapper.toResponse(saved);
    }

    @Override
    public ReservationResponse updateReservation(Long idReservation, ReservationRequest r) {
        log.info("Mise à jour de la réservation {}", idReservation);
        Reservation existing = retrieveReservationEntity(idReservation);
        existing.setDateDebut(r.getDateDebut());
        existing.setDateFin(r.getDateFin());
        Reservation updated = reservationRepository.save(existing);
        log.info("Réservation {} mise à jour", idReservation);
        return ReservationMapper.toResponse(updated);
    }

    @Override
    public ReservationResponse retrieveReservation(Long idReservation) {
        return ReservationMapper.toResponse(retrieveReservationEntity(idReservation));
    }

    @Override
    public void removeReservation(Long idReservation) {
        checkExistence(idReservation);
        reservationRepository.deleteById(idReservation);
        log.info("Réservation {} supprimée", idReservation);
    }

    @Override
    public List<ReservationResponse> addReservations(List<ReservationRequest> reservations) {
        log.info("Ajout de {} réservations", reservations.size());
        return reservationRepository.saveAll(reservations.stream().map(ReservationMapper::toEntity).toList()).stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }

    private Reservation retrieveReservationEntity(Long idReservation) {
        return reservationRepository.findById(idReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation", idReservation));
    }

    private void checkExistence(Long idReservation) {
        if (!reservationRepository.existsById(idReservation)) {
            throw new ResourceNotFoundException("Réservation", idReservation);
        }
    }
}
