package tn.esprit.autoloc.mapper;

import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.EquipementRequest;
import tn.esprit.autoloc.dto.EquipementResponse;

/** Mapper entre l'entité {@link Equipement} et ses DTO. */
public final class EquipementMapper {

    private EquipementMapper() {
    }

    public static Equipement toEntity(EquipementRequest request) {
        Equipement equipement = new Equipement();
        equipement.setLibelle(request.getLibelle());
        return equipement;
    }

    public static EquipementResponse toResponse(Equipement equipement) {
        return EquipementResponse.builder()
                .idEquipement(equipement.getIdEquipement())
                .libelle(equipement.getLibelle())
                .build();
    }
}
