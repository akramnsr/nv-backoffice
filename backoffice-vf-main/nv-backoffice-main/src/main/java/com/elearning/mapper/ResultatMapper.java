// src/main/java/com/elearning/mapper/ResultatMapper.java
package com.elearning.mapper;

import com.elearning.dto.ResultatDto;
import com.elearning.model.Resultat;
import org.springframework.stereotype.Component;

@Component
public class ResultatMapper {

    public ResultatDto toDto(Resultat r) {
        return new ResultatDto(
                (long) r.getId(),
                Double.valueOf(r.getScore())
        );
    }

    public Resultat toEntity(ResultatDto dto) {
        Resultat r = new Resultat();
        r.setId(dto.getId());
        r.setScore((double) dto.getScore().floatValue());
        return r;
    }
}
