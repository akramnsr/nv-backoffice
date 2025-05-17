// FormationMapperImpl.java
package com.elearning.mapper;

import com.elearning.dto.FormationDto;
import com.elearning.model.Formation;
import org.springframework.stereotype.Component;

@Component
public class FormationMapperImpl implements FormationMapper {

    @Override
    public FormationDto toDto(Formation f) {
        if (f == null) return null;
        FormationDto dto = new FormationDto();
        dto.setId(f.getId());
        dto.setTitre(f.getTitre());
        dto.setDescription(f.getDescription());
        dto.setDureeHeures(f.getDureeHeures());
        dto.setImageUrl(f.getImageUrl());
        return dto;
    }

    @Override
    public Formation toEntity(FormationDto dto) {
        if (dto == null) return null;
        Formation f = new Formation();
        f.setId(dto.getId());
        f.setTitre(dto.getTitre());
        f.setDescription(dto.getDescription());
        f.setDureeHeures(dto.getDureeHeures());
        f.setImageUrl(dto.getImageUrl());
        return f;
    }
}
