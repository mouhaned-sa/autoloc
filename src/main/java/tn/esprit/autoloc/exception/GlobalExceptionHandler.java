package tn.esprit.autoloc.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Gestion centralisée des erreurs de tous les contrôleurs de l'API.
 * Chaque exception interceptée est transformée en réponse {@link ErrorResponse} homogène.
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Ressource introuvable -> 404 NOT FOUND. */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Ressource introuvable : {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    /** Contrôle de saisie invalidé par @Valid sur le corps de la requête -> 400 BAD REQUEST. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.warn("Contrôle de saisie échoué : {}", errors);
        return buildResponse(HttpStatus.BAD_REQUEST, "Le corps de la requête est invalide", errors);
    }

    /** Contrôle de saisie invalidé sur les paramètres de méthode (@PathVariable/@RequestParam) -> 400. */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleMethodValidationErrors(HandlerMethodValidationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (var result : ex.getParameterValidationResults()) {
            String parameterName = result.getMethodParameter().getParameterName();
            for (MessageSourceResolvable error : result.getResolvableErrors()) {
                errors.putIfAbsent(parameterName, error.getDefaultMessage());
            }
        }
        log.warn("Paramètre de requête invalide : {}", errors);
        return buildResponse(HttpStatus.BAD_REQUEST, "Paramètre de requête invalide", errors);
    }

    /** Contrôle de saisie invalidé par une contrainte directe (@Validated) -> 400. */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations()
                .forEach(v -> errors.put(v.getPropertyPath().toString(), v.getMessage()));
        log.warn("Contrainte violée : {}", errors);
        return buildResponse(HttpStatus.BAD_REQUEST, "Paramètre de requête invalide", errors);
    }

    /** Type incompatible dans le chemin ou les paramètres (ex : id non numérique) -> 400. */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "La valeur '" + ex.getValue() + "' n'est pas valide pour le paramètre '" + ex.getName() + "'";
        log.warn("Type incompatible : {}", message);
        return buildResponse(HttpStatus.BAD_REQUEST, message, null);
    }

    /** JSON illisible : champ mal saisi ou valeur d'énumération inexistante -> 400. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableBody(HttpMessageNotReadableException ex) {
        log.warn("Corps de requête illisible : {}", ex.getMostSpecificCause().getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Corps de la requête illisible (valeur ou format incorrect)", null);
    }

    /** Violation d'une contrainte en base (unicité : email, immatriculation...) -> 409 CONFLICT. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("Violation de contrainte d'intégrité : {}", ex.getMostSpecificCause().getMessage());
        return buildResponse(HttpStatus.CONFLICT,
                "Violation de contrainte en base : une ressource similaire existe déjà", null);
    }

    /** Toute autre erreur technique non prévue -> 500 INTERNAL SERVER ERROR. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        log.error("Erreur inattendue", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne du serveur", null);
    }

    /** Construit la réponse d'erreur standardisée. */
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, Map<String, String> errors) {
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .errors(errors)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
