// src/main/java/com/elearning/repository/QuizRepository.java
package com.elearning.repository;

import com.elearning.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface QuizRepository extends JpaRepository<Quiz, Long> { }
