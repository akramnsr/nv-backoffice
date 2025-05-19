// src/main/java/com/elearning/service/QuizService.java
package com.elearning.service;

import com.elearning.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface QuizService {
    Page<Quiz> findAll(Pageable pageable);
    Optional<Quiz> findById(Long id);
    Quiz save(Quiz quiz);
    void delete(Long id);
    long countAll();
    Optional<Quiz> findByIdWithQuestions(Long id);

}
