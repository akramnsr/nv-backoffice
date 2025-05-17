// src/main/java/com/elearning/admin/controller/AdminRapportEtuMvcController.java
package com.elearning.admin.controller;

import com.elearning.model.RapportEtu;
import com.elearning.model.User;
import com.elearning.model.Formation;
import com.elearning.repository.RapportEtuRepository;
import com.elearning.service.UserService;
import com.elearning.service.FormationService;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/rapports-etu")
public class AdminRapportEtuMvcController {

    private final RapportEtuRepository repo;
    private final UserService userService;
    private final FormationService formationService;

    public AdminRapportEtuMvcController(RapportEtuRepository repo,
                                        UserService userService,
                                        FormationService formationService) {
        this.repo = repo;
        this.userService = userService;
        this.formationService = formationService;
    }

    @ModelAttribute("rapportEtu")
    public RapportEtu rapportEtu() {
        return new RapportEtu();
    }

    /** 1. Liste */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("rapports", repo.findAll());
        return "admin/rapports-etu-list";
    }

    /** 2. Formulaire de création */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("etudiants", userService.findAllByRoleName("ETUDIANT"));
        model.addAttribute("formations", formationService.findAll(Pageable.unpaged()).getContent());
        return "admin/rapports-etu-form";
    }

    /** 3. Création */
    @PostMapping
    public String create(@ModelAttribute("rapportEtu") @Valid RapportEtu rapportEtu,
                         BindingResult br,
                         @RequestParam("etudiantId") Long etudiantId,
                         @RequestParam("formationId") Long formationId) {
        if (br.hasErrors()) {
            return "admin/rapports-etu-form";
        }
        // affectation étudiant + formation + date
        User u = userService.findById(etudiantId)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant invalide"));
        Formation f = formationService.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation invalide"));
        rapportEtu.setEtudiant(u);
        rapportEtu.setFormation(f);
        rapportEtu.setDateSoumission(new Date());
        repo.save(rapportEtu);
        return "redirect:/admin/rapports-etu";
    }

    /** 4. Formulaire d’édition */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        RapportEtu r = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rapport introuvable"));
        model.addAttribute("rapportEtu", r);
        model.addAttribute("etudiants", userService.findAllByRoleName("ETUDIANT"));
        model.addAttribute("formations", formationService.findAll(Pageable.unpaged()).getContent());
        return "admin/rapports-etu-form";
    }

    /** 5. Mise à jour */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("rapportEtu") @Valid RapportEtu rapportEtu,
                         BindingResult br,
                         @RequestParam("etudiantId") Long etudiantId,
                         @RequestParam("formationId") Long formationId) {
        if (br.hasErrors()) {
            return "admin/rapports-etu-form";
        }
        User u = userService.findById(etudiantId)
                .orElseThrow(() -> new IllegalArgumentException("Étudiant invalide"));
        Formation f = formationService.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation invalide"));
        rapportEtu.setId(id);
        rapportEtu.setEtudiant(u);
        rapportEtu.setFormation(f);
        // dateSoumission reste celle d'origine ou à rafraîchir si nécessaire
        repo.save(rapportEtu);
        return "redirect:/admin/rapports-etu";
    }

    /** 6. Suppression */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin/rapports-etu";
    }

    /** 7. Détail */
    @GetMapping("/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        RapportEtu r = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rapport introuvable"));
        model.addAttribute("rapport", r);
        return "admin/rapport-etu-details";
    }
}
