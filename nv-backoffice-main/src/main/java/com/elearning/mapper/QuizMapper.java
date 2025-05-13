package com.elearning.mapper;

import com.elearning.dto.QuizDto;
import com.elearning.model.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    public QuizDto toDto(Quiz q) {
        return new QuizDto(
                q.getId(),
                q.getTitre(),
                q.getDescription()
        );
    }

    public Quiz toEntity(QuizDto dto) {
        Quiz q = new Quiz();
        q.setId(dto.getId());
        // on prend dto.getTitre() (et non getTitle())
        q.setTitre(dto.getTitre());
        q.setDescription(dto.getDescription());
        return q;
    }
}
