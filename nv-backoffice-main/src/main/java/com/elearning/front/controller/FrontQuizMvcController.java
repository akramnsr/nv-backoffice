package com.elearning.front.controller;

import com.elearning.model.Quiz;
import com.elearning.service.QuizService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/quizzes")
public class FrontQuizMvcController {

    private final QuizService service;

    public FrontQuizMvcController(QuizService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        List<Quiz> quizzes = service
                .findAll(Pageable.unpaged())
                .getContent();
        model.addAttribute("quizzes", quizzes);
        return "front/quiz-list";
    }
}
