package com.elearning.repository;

import com.elearning.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Query("SELECT DISTINCT q FROM Quiz q " +
            "LEFT JOIN FETCH q.questions qu " +
            "LEFT JOIN FETCH qu.choices " +
            "WHERE q.id = :id")
    Optional<Quiz> findByIdWithQuestions(@Param("id") Long id);


}
