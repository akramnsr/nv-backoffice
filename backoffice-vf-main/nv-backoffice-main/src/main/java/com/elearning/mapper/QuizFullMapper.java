package com.elearning.mapper;

import com.elearning.dto.*;
import com.elearning.model.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class QuizFullMapper {
    public QuizFullDto toDto(Quiz quiz) {
        QuizFullDto dto = new QuizFullDto();
        dto.setId(quiz.getId());
        dto.setTitre(quiz.getTitre());
        dto.setDescription(quiz.getDescription());
        dto.setImageUrl(quiz.getImageUrl());
        if (quiz.getQuestions() != null) {
            dto.setQuestions(
                    quiz.getQuestions().stream().map(q -> {
                        QuestionDto qdto = new QuestionDto();
                        qdto.setId(q.getId());
                        qdto.setLibelle(q.getLibelle());
                        if (q.getChoices() != null) {
                            qdto.setChoices(
                                    q.getChoices().stream().map(c -> {
                                        ChoiceDto cdto = new ChoiceDto();
                                        cdto.setId(c.getId());
                                        cdto.setTexte(c.getTexte());
                                        // ASTUCE : Cacher le champ "correct" (ne pas envoyer au front si tu veux éviter la triche)
                                        cdto.setCorrect(false);
                                        return cdto;
                                    }).collect(Collectors.toList())
                            );
                        }
                        return qdto;
                    }).collect(Collectors.toList())
            );
        }
        return dto;
    }
}
