// src/main/java/com/elearning/service/QuestionService.java
package com.elearning.service;
import com.elearning.model.Question;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> findByQuiz(Long quizId);
    Optional<Question> findById(Long id);
    Question save(Question q);
    void delete(Long id);
}
