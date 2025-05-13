package com.elearning.admin.controller;

import com.elearning.model.User;
import com.elearning.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserMvcController {

    private final UserService service;

    public AdminUserMvcController(UserService service) {
        this.service = service;
    }

    /** LISTE DES UTILISATEURS */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", service.findAll(Pageable.unpaged()).getContent());
        return "admin/user-list";
    }

    /** FORMULAIRE DE CRÉATION */
    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user-form";
    }

    /** CRÉATION */
    @PostMapping
    public String create(
            @ModelAttribute("user") @Valid User user,
            BindingResult binding
    ) {
        if (binding.hasErrors()) {
            return "admin/user-form";
        }
        service.save(user);
        return "redirect:/admin/users";
    }

    /** FORMULAIRE D’ÉDITION */
    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        User user = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable pour l'ID " + id));
        model.addAttribute("user", user);
        return "admin/user-form";
    }

    /** MISE À JOUR */
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("user") @Valid User user,
            BindingResult binding
    ) {
        if (binding.hasErrors()) {
            return "admin/user-form";
        }
        // s’assurer qu’on met bien à jour l’utilisateur existant
        user.setId(id);
        service.save(user);
        return "redirect:/admin/users";
    }

    /** SUPPRESSION */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/users";
    }
}
