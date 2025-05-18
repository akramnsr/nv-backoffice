// src/main/java/com/elearning/service/impl/QuestionServiceImpl.java
package com.elearning.service.impl;
import com.elearning.model.Question;
import com.elearning.repository.QuestionRepository;
import com.elearning.repository.QuizRepository;
import com.elearning.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service @Transactional
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository repo;
    public QuestionServiceImpl(QuestionRepository repo) { this.repo = repo; }
    @Override
    public List<Question> findByQuiz(Long quizId) {
        List<Question> questions = repo.findAllByQuizId(quizId);
        // initialise q.getChoices() pour chaque question
        questions.forEach(q -> q.getChoices().size());
        return questions;
    }


    @Override
    public Optional<Question> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Question save(Question q) {
        // cascade sauvegarde des choices
        q.getChoices().forEach(c -> c.setQuestion(q));
        return repo.save(q);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Question introuvable pour l'ID " + id);
        }
        repo.deleteById(id);
    }

}
