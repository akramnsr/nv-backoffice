package com.elearning.admin.controller;

import com.elearning.model.Quiz;
import com.elearning.service.QuizService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/quiz")
public class AdminQuizMvcController {

    private final QuizService service;

    public AdminQuizMvcController(QuizService service) {
        this.service = service;
    }

    /** Liste non paginée */
    @GetMapping
    public String list(Model model) {
        List<Quiz> quizzes = service
                .findAll(Pageable.unpaged())
                .getContent();
        model.addAttribute("quizzes", quizzes);
        return "admin/quiz-list";
    }

    /** Formulaire de création */
    @GetMapping("/nouveau")
    public String showCreateForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "admin/quiz-form";
    }

    /** Sauvegarde création */
    @PostMapping
    public String create(@ModelAttribute Quiz quiz) {
        service.save(quiz);
        return "redirect:/admin/quiz";
    }

    @GetMapping("/{id}/modifier")
    public String showEditForm(@PathVariable Long id, Model model) {
        Quiz quiz = service.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Quiz introuvable pour l'ID " + id)
                );
        model.addAttribute("quiz", quiz);
        return "admin/quiz-form";
    }


    /** Sauvegarde édition */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Quiz quiz) {
        quiz.setId(id);
        service.save(quiz);
        return "redirect:/admin/quiz";
    }

    /** Suppression */
    @GetMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/quiz";
    }
}
