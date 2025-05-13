package com.elearning.admin.controller;

import com.elearning.model.Formation;
import com.elearning.service.FormationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/formations")
public class AdminFormationMvcController {

    private final FormationService service;

    public AdminFormationMvcController(FormationService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        List<Formation> formations = service
                .findAll(Pageable.unpaged())
                .getContent();
        model.addAttribute("formations", formations);
        return "admin/formation-list";
    }

    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("formation", new Formation());
        return "admin/formation-form";
    }

    @PostMapping
    public String save(@ModelAttribute Formation formation) {
        service.save(formation);
        return "redirect:/admin/formations";
    }

    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        Formation formation = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable pour l'ID " + id));
        model.addAttribute("formation", formation);
        return "admin/formation-form";
    }


    @GetMapping("/{id}/confirmer-suppression")
    public String afficherConfirmationSuppression(@PathVariable Long id, Model model) {
        model.addAttribute("formation", service.findById(id));
        return "admin/supprimer";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimerFormation(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/formations";
    }
}
