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

    private final QuizRepository repository;

    public QuizServiceImpl(QuizRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Quiz> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Quiz save(Quiz quiz) {
        return repository.save(quiz);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Quiz introuvable pour l'ID " + id);
        }
        repository.deleteById(id);
    }
}
