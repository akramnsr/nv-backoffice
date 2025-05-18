// src/main/java/com/elearning/repository/ChoiceRepository.java
package com.elearning.repository;

import com.elearning.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice,Long> {
    List<Choice> findAllByQuestionId(Long questionId);
}
