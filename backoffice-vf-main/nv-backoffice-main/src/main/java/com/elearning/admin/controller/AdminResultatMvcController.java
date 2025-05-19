// src/main/java/com/elearning/admin/controller/AdminResultatMvcController.java
package com.elearning.admin.controller;

import com.elearning.model.Quiz;
import com.elearning.model.Resultat;
import com.elearning.model.User;
import com.elearning.service.QuizService;
import com.elearning.service.ResultatService;
import com.elearning.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/resultats")
public class AdminResultatMvcController {

    private final ResultatService resultatService;
    private final UserService userService;
    private final QuizService quizService;

    public AdminResultatMvcController(ResultatService resultatService,
                                      UserService userService,
                                      QuizService quizService) {
        this.resultatService = resultatService;
        this.userService      = userService;
        this.quizService      = quizService;
    }

    /** 1. Liste des résultats */
    @GetMapping
    public String list(Model model) {
        List<Resultat> resultats = resultatService
                .findAll(Pageable.unpaged())
                .getContent();
        model.addAttribute("resultats", resultats);
        return "admin/resultat-list";
    }

    /** 2. Formulaire de création */
    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("resultat", new Resultat());
        model.addAttribute("etudiants",
                userService.findAllByRoleName("ETUDIANT"));
        model.addAttribute("quizzes",
                quizService.findAll(Pageable.unpaged()).getContent());
        return "admin/resultat-form";
    }

    /** 3. Sauvegarde création */
    @PostMapping
    public String save(@ModelAttribute Resultat resultat,
                       @RequestParam("etudiantId") Long etudiantId,
                       @RequestParam("quizId") Long quizId) {

        User etu = userService.findById(etudiantId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Étudiant invalide : " + etudiantId)
                );
        Quiz q = quizService.findById(quizId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Quiz invalide : " + quizId)
                );

        resultat.setEtudiant(etu);
        resultat.setQuiz(q);
        resultatService.save(resultat);
        return "redirect:/admin/resultats";
    }

    /** 4. Formulaire d’édition */
    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        Resultat r = resultatService.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("ID invalide: " + id));
        model.addAttribute("resultat", r);
        model.addAttribute("etudiants",
                userService.findAllByRoleName("ETUDIANT"));
        model.addAttribute("quizzes",
                quizService.findAll(Pageable.unpaged()).getContent());
        return "admin/resultat-form";
    }

    /** 5. Sauvegarde édition */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Resultat resultat,
                         @RequestParam("etudiantId") Long etudiantId,
                         @RequestParam("quizId") Long quizId) {

        User etu = userService.findById(etudiantId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Étudiant invalide : " + etudiantId)
                );
        Quiz q = quizService.findById(quizId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Quiz invalide : " + quizId)
                );

        resultat.setId(id);
        resultat.setEtudiant(etu);
        resultat.setQuiz(q);
        resultatService.save(resultat);
        return "redirect:/admin/resultats";
    }

    /** 6. Suppression */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        resultatService.delete(id);
        return "redirect:/admin/resultats";
    }


}
