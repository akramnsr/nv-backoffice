package com.elearning.mapper;

import com.elearning.dto.FormationDto;
import com.elearning.model.Formation;
import org.springframework.stereotype.Component;

@Component
public class FormationMapperImpl implements FormationMapper {

    @Override
    public FormationDto toDto(Formation f) {
        if (f == null) return null;
        return new FormationDto(
                f.getId(),
                f.getTitre(),
                f.getDescription(),
                f.getDureeHeures()
        );
    }

    @Override
    public Formation toEntity(FormationDto dto) {
        if (dto == null) return null;
        Formation f = new Formation();
        f.setId(dto.getId());
        f.setTitre(dto.getTitre());
        f.setDescription(dto.getDescription());
        f.setDureeHeures(dto.getDureeHeures());
        return f;
    }
}
