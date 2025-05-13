// src/main/java/com/elearning/admin/controller/AdminRapportEtuMvcController.java
package com.elearning.admin.controller;

import com.elearning.model.RapportEtu;
import com.elearning.model.User;
import com.elearning.repository.RapportEtuRepository;
import com.elearning.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    // 1. Liste
    @GetMapping
    public String list(Model model) {
        model.addAttribute("rapports", repo.findAll());
        return "/admin/rapports-etu-list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("rapportEtu", new RapportEtu());
        model.addAttribute("etudiants",
                userService.findAll(Pageable.unpaged()).getContent());
        return "admin/rapports-etu-form";   // plus de slash initial
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        RapportEtu r = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID invalide: " + id));
        model.addAttribute("rapportEtu", r);
        model.addAttribute("etudiants",
                userService.findAll(Pageable.unpaged()).getContent());
        return "admin/rapports-etu-form";
    }


    @PostMapping
    public String create(@ModelAttribute RapportEtu rapportEtu,
                         @RequestParam("etudiant.id") Long etudiantId) {
        User u = userService.findById(etudiantId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Étudiant invalide : " + etudiantId)
                );
        rapportEtu.setEtudiant(u);
        repo.save(rapportEtu);
        return "redirect:/admin/rapports-etu";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute RapportEtu rapportEtu,
                         @RequestParam("etudiant.id") Long etudiantId) {
        User u = userService.findById(etudiantId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Étudiant invalide : " + etudiantId)
                );
        rapportEtu.setId(id);
        rapportEtu.setEtudiant(u);
        repo.save(rapportEtu);
        return "redirect:/admin/rapports-etu";
    }


    // 6. Suppression
    @PostMapping("/{id}/supprimer")
    public String supprimerRapport(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin/rapports-etu";
    }
}
