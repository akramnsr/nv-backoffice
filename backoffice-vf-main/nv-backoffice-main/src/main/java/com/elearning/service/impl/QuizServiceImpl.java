// src/main/java/com/elearning/service/impl/QuizServiceImpl.java
package com.elearning.service.impl;

import com.elearning.model.Quiz;
import com.elearning.repository.QuizRepository;
import com.elearning.service.QuizService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private final QuizRepository repo;

    public QuizServiceImpl(QuizRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<Quiz> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return Optional.of(repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quiz non trouvé pour l'ID " + id))
        );
    }

    @Override
    public Quiz save(Quiz quiz) {
        return repo.save(quiz);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Quiz introuvable pour l'ID " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public long countAll() {
        return repo.count();
    }

    @Override
    public Optional<Quiz> findByIdWithQuestions(Long id) {
        return repo.findByIdWithQuestions(id);
    }

}
