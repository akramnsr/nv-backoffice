// src/main/java/com/elearning/mapper/QuestionMapper.java
package com.elearning.mapper;
import com.elearning.dto.QuestionDto;
import com.elearning.dto.ChoiceDto;
import com.elearning.model.Question;
import com.elearning.model.Choice;
import com.elearning.model.Quiz;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;


@Component
public class QuestionMapper {
    public QuestionDto toDto(Question q) {
        QuestionDto dto = new QuestionDto();
        dto.setId(q.getId());
        dto.setLibelle(q.getLibelle());
        dto.setQuizId(q.getQuiz().getId());
        dto.setChoices(q.getChoices().stream().map(c->{
            ChoiceDto cd=new ChoiceDto();
            cd.setId(c.getId());
            cd.setTexte(c.getTexte());
            cd.setCorrect(c.isCorrect());
            return cd;
        }).collect(Collectors.toList()));
        return dto;
    }
    public Question toEntity(QuestionDto dto, Quiz quiz) {
        Question q = new Question();
        q.setId(dto.getId());
        q.setLibelle(dto.getLibelle());
        q.setQuiz(quiz);   // <-- setter de l’entité

        q.setChoices(
                new HashSet<>( // <-- HashSet au lieu de ArrayList
                        dto.getChoices().stream()
                        .map(cd -> {
                            Choice c = new Choice();
                            c.setId(cd.getId());
                            c.setTexte(cd.getTexte());
                            c.setCorrect(cd.isCorrect());
                            c.setQuestion(q);
                            return c;
                        })
                        .collect(Collectors.toList())
                )
        );

        return q;
    }
}
