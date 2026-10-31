package tn.esprit.autoloc.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Structure standardisée renvoyée au client en cas d'erreur HTTP.
 * Utilisée par {@link GlobalExceptionHandler} pour toutes les réponses d'erreur.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    /** Date et heure de survenue de l'erreur. */
    private LocalDateTime timestamp;

    /** Code HTTP renvoyé (400, 404, 409, 500...). */
    private int status;

    /** Raison HTTP (Bad Request, Not Found...). */
    private String error;

    /** Message général de l'erreur. */
    private String message;

    /** Détail champ par champ (contrôle de saisie) : nom du champ -> message d'erreur. */
    private Map<String, String> errors;
}
