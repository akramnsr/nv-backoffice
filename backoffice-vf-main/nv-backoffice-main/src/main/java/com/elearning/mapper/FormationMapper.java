// FormationMapper.java
package com.elearning.mapper;

import com.elearning.dto.FormationDto;
import com.elearning.model.Formation;

public interface FormationMapper {
    FormationDto toDto(Formation f);
    Formation toEntity(FormationDto dto);
}
