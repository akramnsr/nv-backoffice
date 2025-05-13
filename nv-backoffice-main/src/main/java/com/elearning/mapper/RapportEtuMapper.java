// src/main/java/com/elearning/mapper/RapportEtuMapper.java
package com.elearning.mapper;

import com.elearning.dto.RapportEtuDto;
import com.elearning.model.RapportEtu;
import org.springframework.stereotype.Component;

@Component
public class RapportEtuMapper {

    public RapportEtuDto toDto(RapportEtu r) {
        return new RapportEtuDto(
                (long) r.getId(),
                r.getCommentaireFormateur()
        );
    }

    public RapportEtu toEntity(RapportEtuDto dto) {
        RapportEtu r = new RapportEtu();
        r.setId(dto.getId());
        r.setCommentaireFormateur(dto.getCommentaire());
        return r;
    }
}
