package com.elearning.admin.controller;

import com.elearning.model.Formation;
import com.elearning.model.Quiz;
import com.elearning.service.FormationService;
import com.elearning.service.QuizService;
import com.elearning.service.StorageService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/quiz")
public class AdminQuizMvcController {

    private final QuizService quizService;
    private final FormationService formationService;
    private final StorageService storageService;

    public AdminQuizMvcController(QuizService quizService,
                                  FormationService formationService,
                                  StorageService storageService) {
        this.quizService      = quizService;
        this.formationService = formationService;
        this.storageService   = storageService;
    }

    @GetMapping
    public String list(Model model) {
        List<Quiz> quizzes = quizService.findAll(Pageable.unpaged()).getContent();
        model.addAttribute("quizzes", quizzes);
        return "admin/quiz-list";
    }

    @GetMapping("/nouveau")
    public String showCreateForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("formations",
                formationService.findAll(Pageable.unpaged()).getContent());
        return "admin/quiz-form";
    }

    @PostMapping
    public String create(@ModelAttribute Quiz quiz,
                         @RequestParam("formationId") Long formationId,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        Formation f = formationService.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable"));
        quiz.setFormation(f);
        if (imageFile != null && !imageFile.isEmpty()) {
            quiz.setImageUrl(storageService.store(imageFile));
        }
        quizService.save(quiz);
        return "redirect:/admin/quiz";
    }

    @GetMapping("/{id}/modifier")
    public String showEditForm(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz introuvable"));
        model.addAttribute("quiz", quiz);
        model.addAttribute("formations",
                formationService.findAll(Pageable.unpaged()).getContent());
        return "admin/quiz-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Quiz quiz,
                         @RequestParam("formationId") Long formationId,
                         @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        quiz.setId(id);
        Formation f = formationService.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable"));
        quiz.setFormation(f);
        if (imageFile != null && !imageFile.isEmpty()) {
            quiz.setImageUrl(storageService.store(imageFile));
        }
        quizService.save(quiz);
        return "redirect:/admin/quiz";
    }

    /** Suppression via POST */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        quizService.delete(id);
        return "redirect:/admin/quiz";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz introuvable pour l'ID " + id));
        model.addAttribute("quiz", quiz);
        return "admin/quiz-details";
    }
}
