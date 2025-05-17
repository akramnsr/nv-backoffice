// src/main/java/com/elearning/admin/controller/AdminQuizMvcController.java
package com.elearning.admin.controller;

import com.elearning.model.Formation;
import com.elearning.model.Quiz;
import com.elearning.service.FormationService;
import com.elearning.service.QuizService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin/quiz")
public class AdminQuizMvcController {

    private final QuizService quizService;
    private final FormationService formationService;

    public AdminQuizMvcController(QuizService quizService,
                                  FormationService formationService) {
        this.quizService = quizService;
        this.formationService = formationService;
    }

    /** Liste non paginée */
    @GetMapping
    public String list(Model model) {
        List<Quiz> quizzes = quizService.findAll(Pageable.unpaged()).getContent();
        model.addAttribute("quizzes", quizzes);
        return "admin/quiz-list";
    }

    /** Formulaire de création */
    @GetMapping("/nouveau")
    public String showCreateForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("formations",
                formationService.findAll(Pageable.unpaged()).getContent()
        );
        return "admin/quiz-form";
    }

    /** Sauvegarde création */
    @PostMapping
    public String create(@ModelAttribute Quiz quiz,
                         @RequestParam("formationId") Long formationId) {
        Formation f = formationService.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable"));
        quiz.setFormation(f);
        quizService.save(quiz);
        return "redirect:/admin/quiz";
    }

    /** Formulaire d’édition */
    @GetMapping("/{id}/modifier")
    public String showEditForm(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz introuvable pour l'ID " + id));
        model.addAttribute("quiz", quiz);
        model.addAttribute("formations",
                formationService.findAll(Pageable.unpaged()).getContent()
        );
        return "admin/quiz-form";
    }

    /** Sauvegarde édition */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Quiz quiz,
                         @RequestParam("formationId") Long formationId) {
        quiz.setId(id);
        Formation f = formationService.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable"));
        quiz.setFormation(f);
        quizService.save(quiz);
        return "redirect:/admin/quiz";
    }

    /** Suppression */
    @GetMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        quizService.delete(id);
        return "redirect:/admin/quiz";
    }
}
