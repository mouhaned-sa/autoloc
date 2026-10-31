package tn.esprit.autoloc.exception;

/**
 * Exception levée quand une ressource demandée n'existe pas en base.
 * Interceptée par {@link GlobalExceptionHandler} qui la transforme en réponse HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " introuvable avec l'id : " + id);
    }
}
