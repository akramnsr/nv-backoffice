// src/main/java/com/elearning/admin/controller/AdminRapportEtuMvcController.java
package com.elearning.admin.controller;

import com.elearning.model.RapportEtu;
import com.elearning.model.User;
import com.elearning.repository.RapportEtuRepository;
import com.elearning.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/rapports-etu")
public class AdminRapportEtuMvcController {

    private final RapportEtuRepository repo;
    private final UserService userService;

    public AdminRapportEtuMvcController(RapportEtuRepository repo,
                                        UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    /** Liste de tous les rapports */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("rapports", repo.findAll());
        return "admin/rapports-etu-list";
    }

    /** Formulaire de création (seulement étudiants) */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("rapportEtu", new RapportEtu());
        List<User> etudiants = userService.findAllByRoleName("ETUDIANT");
        model.addAttribute("etudiants", etudiants);
        return "admin/rapports-etu-form";
    }

    /** Formulaire d’édition (seulement étudiants) */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        RapportEtu r = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID invalide: " + id));
        model.addAttribute("rapportEtu", r);
        List<User> etudiants = userService.findAllByRoleName("ETUDIANT");
        model.addAttribute("etudiants", etudiants);
        return "admin/rapports-etu-form";
    }

    /** Création */
    @PostMapping
    public String create(@ModelAttribute RapportEtu rapportEtu,
                         @RequestParam("etudiant.id") Long etudiantId) {
        User u = userService.findById(etudiantId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Étudiant invalide : " + etudiantId));
        rapportEtu.setEtudiant(u);
        repo.save(rapportEtu);
        return "redirect:/admin/rapports-etu";
    }

    /** Mise à jour */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute RapportEtu rapportEtu,
                         @RequestParam("etudiant.id") Long etudiantId) {
        User u = userService.findById(etudiantId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Étudiant invalide : " + etudiantId));
        rapportEtu.setId(id);
        rapportEtu.setEtudiant(u);
        repo.save(rapportEtu);
        return "redirect:/admin/rapports-etu";
    }

    /** Suppression */
    @PostMapping("/{id}/supprimer")
    public String supprimerRapport(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin/rapports-etu";
    }
}
