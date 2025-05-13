package com.elearning.admin.controller;

import com.elearning.model.Resultat;
import com.elearning.model.User;
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

    public AdminResultatMvcController(ResultatService resultatService,
                                      UserService userService) {
        this.resultatService = resultatService;
        this.userService      = userService;
    }

    @GetMapping
    public String list(Model model) {
        List<Resultat> resultats = resultatService.findAll(Pageable.unpaged()).getContent();
        model.addAttribute("resultats", resultats);
        return "admin/resultat-list";
    }

    /** Formulaire de création */
    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("resultat", new Resultat());
        // on passe la liste des étudiants
        List<User> etudiants = userService.findAll(Pageable.unpaged()).getContent();
        model.addAttribute("etudiants", etudiants);
        return "admin/resultat-form";
    }

    /** Sauvegarde création/édition */
    @PostMapping
    public String save(@ModelAttribute Resultat resultat) {
        resultatService.save(resultat);
        return "redirect:/admin/resultats";
    }

    /** Formulaire d’édition */
    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        Resultat r = resultatService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID invalide: " + id));
        model.addAttribute("resultat", r);
        // idem on repasse la liste des étudiants
        List<User> etudiants = userService.findAll(Pageable.unpaged()).getContent();
        model.addAttribute("etudiants", etudiants);
        return "admin/resultat-form";
    }

    /** Suppression */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        resultatService.delete(id);
        return "redirect:/admin/resultats";
    }
}
