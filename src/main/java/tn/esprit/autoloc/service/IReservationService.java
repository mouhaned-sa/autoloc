package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.ReservationRequest;
import tn.esprit.autoloc.dto.ReservationResponse;
import java.util.List;

public interface IReservationService {
    List<ReservationResponse> retrieveAllReservations();
    ReservationResponse addReservation(ReservationRequest r);
    ReservationResponse updateReservation(Long idReservation, ReservationRequest r);
    ReservationResponse retrieveReservation(Long idReservation);
    void removeReservation(Long idReservation);
    List<ReservationResponse> addReservations(List<ReservationRequest> reservations);
}
