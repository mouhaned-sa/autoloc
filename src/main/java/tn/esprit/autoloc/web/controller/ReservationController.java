package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.ReservationRequest;
import tn.esprit.autoloc.dto.ReservationResponse;
import tn.esprit.autoloc.service.IReservationService;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @GetMapping
    public List<ReservationResponse> retrieveAllReservations() {
        return reservationService.retrieveAllReservations();
    }

    @PostMapping
    public ReservationResponse addReservation(@Valid @RequestBody ReservationRequest r) {
        return reservationService.addReservation(r);
    }

    @PutMapping("/{id}")
    public ReservationResponse updateReservation(@PathVariable @Positive Long id,
                                                 @Valid @RequestBody ReservationRequest r) {
        return reservationService.updateReservation(id, r);
    }

    @GetMapping("/{id}")
    public ReservationResponse retrieveReservation(@PathVariable @Positive Long id) {
        return reservationService.retrieveReservation(id);
    }

    @DeleteMapping("/{id}")
    public void removeReservation(@PathVariable @Positive Long id) {
        reservationService.removeReservation(id);
    }
}
