package tn.esprit.autoloc.mapper;

import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ReservationRequest;
import tn.esprit.autoloc.dto.ReservationResponse;

/** Mapper entre l'entité {@link Reservation} et ses DTO. */
public final class ReservationMapper {

    private ReservationMapper() {
    }

    public static Reservation toEntity(ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(request.getDateDebut());
        reservation.setDateFin(request.getDateFin());
        return reservation;
    }

    public static ReservationResponse toResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .idReservation(reservation.getIdReservation())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .statut(reservation.getStatut())
                .build();
    }
}
