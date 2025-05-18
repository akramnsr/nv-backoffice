// src/main/java/com/elearning/repository/QuestionRepository.java
package com.elearning.repository;

import com.elearning.model.Question;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    @EntityGraph(attributePaths = "choices")
    Optional<Question> findById(Long id);
    List<Question> findAllByQuizId(Long quizId);
}
