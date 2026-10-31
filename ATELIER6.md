# Atelier 6 — Clean code : DTO + Validator + GlobalExceptionHandler

## 1. DTO — Transformer la réponse de tous les endpoints

### Pourquoi ?
Renvoyer directement les entités JPA (`domain`) expose toute la table, crée des risques de lazy-loading
et de fuite de données. Un **DTO** envoie uniquement les champs utiles → réponse plus légère et API découplée.

### Ce qui a été fait

| Livrable | Emplacement |
|---|---|
| **DTOs** | `tn.esprit.autoloc.dto` : `XxxRequest` (entrées POST/PUT) + `XxxResponse` (sorties) pour les 9 entités (Agence, Client, Contrat, Employe, Equipement, Maintenance, Paiement, Reservation, Vehicule) |
| **Mappers** | `tn.esprit.autoloc.mapper` : un mapper statique par entité (`toEntity` / `toResponse`) |
| **Services** | `service/IXxxService` + `XxxServiceImpl` reçrivées : reçoivent des `Request`, renvoient des `Response` via les mappers |
| **Controllers** | `web/controller/*` : tous les endpoints acceptent/renvoient des DTOs |

### Changements d'API (à signaler)
- `PUT` prend désormais l'id dans le chemin : `PUT /xxx/{id}` (le corps ne contient plus d'id).
- Un `GET /xxx/{id}` sur un id inexistant renvoie désormais **404 NOT FOUND** (via le gestionnaire global)
  au lieu de `200` avec un corps vide.
- `DELETE` sur un id inexistant renvoie **404** au lieu d'un 500.

---

## 2. Contrôle de saisie (Validator)

Le starter `spring-boot-starter-validation` est déjà présent dans le `pom.xml` (Hibernate Validator).

Annotations appliquées sur les champs des `Request` :
`@NotBlank`, `@NotNull`, `@Size`, `@Email`, `@Positive`, `@PositiveOrZero`, `@Digits`, `@Pattern`,
`@AssertTrue` (contrôle croisé dateDebut < dateFin pour `ReservationRequest` et `MaintenanceRequest`),
contraintes `@Positive` sur les `@PathVariable id`.

Activation : `@Valid` devant chaque `@RequestBody` dans les contrôleurs.

Exemples de contrôle :
- `ClientRequest.email` → `@Email`
- `VehiculeRequest.tarifJournalier` → `@NotNull @Positive @Digits(integer=8, fraction=2)`
- `AgenceRequest.telephone` → `@Pattern(regexp = "^[+\\d\\s]*$")`
- `ReservationRequest` → `@AssertTrue isPeriodeValide()` : dateFin après dateDebut

---

## 3. Classe de gestion des erreurs pour tous les contrôleurs

`tn.esprit.autoloc.exception` :

| Classe | Rôle |
|---|---|
| `ResourceNotFoundException` | levée par les services quand la ressource n'existe pas |
| `ErrorResponse` | corps de réponse standardisé : `timestamp`, `status`, `error`, `message`, `errors` (champ → message) |
| `GlobalExceptionHandler` | `@RestControllerAdvice` qui **centralise le traitement des erreurs de tous les contrôleurs** |

Exceptions interceptées :
| Exception | HTTP |
|---|---|
| `MethodArgumentNotValidException` (contrôle de saisie `@Valid`) | 400 + détail par champ |
| `HandlerMethodValidationException` (`@Positive` sur l'id…) | 400 |
| `MethodArgumentTypeMismatchException` (id non numérique) | 400 |
| `HttpMessageNotReadableException` (JSON invalide / enum inexistante) | 400 |
| `ConstraintViolationException` | 400 |
| `ResourceNotFoundException` | 404 |
| `DataIntegrityViolationException` (unicité email/immatriculation…) | 409 |
| Toute autre | 500 |

---

## 4. SonarQube for IDE (IntelliJ) & corrections

### Installation ("plugin Sonar")
1. `File ▸ Settings ▸ Plugins ▸ Marketplace` → chercher **SonarQube for IDE** (anciennement SonarLint) → Install → redémarrer.
2. Choisir le projet dans l'onglet **SonarQube for IDE** en bas de l'IDE.

### Exemples d'issues Sonar corrigées avant/après
| Issue Sonar | Avant | Après |
|---|---|---|
| Les classes utilitaires ne doivent pas avoir de constructeur public | Mapper sans constructeur privé | `private XxxMapper() {}` (S1118) |
| Ne pas renvoyer `null` (risque NPE) | `orElse(null)` dans les `retrieve` | `orElseThrow(ResourceNotFoundException)` |
| Gestion des cas « rien à faire » | `deleteById(id)` aveugle → 500 si absent | `existsById` puis `deleteById` (404) |
| Logging cohérent | pas de logs métier | `@Slf4j` + logs INFO/WARN (S3447) |
| Duplication de code | checks manuels partout | méthodes privées `retrieveXxxEntity` / `checkExistence` |

*(Imprimer l'onglet SonarQube for IDE avant et après correction pour la remise.)*

---

## 5. Exécution & tests (imprim exécution)

```bash
# démarrage
mvnw spring-boot:run
# Swagger : http://localhost:8080/swagger-ui/index.html
```

Exemples de requêtes :
```bash
# 1. Lister (réponse DTO)
curl http://localhost:8080/agences

# 2. Création valide + réponse DTO
curl -X POST http://localhost:8080/agences -H "Content-Type: application/json" ^
  -d '{"nom":"AutoLoc Tunis","ville":"Tunis","adresse":"Avenue Habib Bourguiba","telephone":"+216 71 000 000"}'

# 3. Contrôle de saisie → 400 avec le détail des champs
curl -X POST http://localhost:8080/clients -H "Content-Type: application/json" ^
  -d '{"nom":"","prenom":"Ali","email":"pas-un-email","numPermis":"","dateInscription":"2026-01-01"}'

# 4. 404 géré par le GlobalExceptionHandler
curl http://localhost:8080/vehicules/9999

# 5. Contrôle sur l'id du chemin
curl -X PUT http://localhost:8080/agences/0 -H "Content-Type: application/json" -d '{"nom":"X","ville":"Y"}'

# 6. Unicité en base → 409
curl -X POST http://localhost:8080/vehicules -H "Content-Type: application/json" ^
  -d '{"immatriculation":"<immat déjà en base>","marque":"Dacia","modele":"Sandero","categorie":"CITADINE","tarifJournalier":90,"statut":"DISPONIBLE"}'
```
