package com.elearning.admin.controller;

import com.elearning.model.Role;
import com.elearning.service.RoleService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/roles")
public class AdminRoleMvcController {

    private final RoleService service;

    public AdminRoleMvcController(RoleService service) {
        this.service = service;
    }

    /** Liste tous les rôles (sans pagination en admin) */
    @GetMapping
    public String list(Model model) {
        // on appelle findAll(Pageable.unpaged()) pour récupérer la liste complète
        List<Role> roles = service.findAll(Pageable.unpaged()).getContent();
        model.addAttribute("roles", roles);
        return "admin/role-list";
    }

    /** Formulaire de création */
    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("role", new Role());
        return "admin/role-form";
    }

    /** Sauvegarde création ou mise à jour */
    @PostMapping
    public String save(@ModelAttribute Role role) {
        service.save(role);
        return "redirect:/admin/roles";
    }

    /** Formulaire d’édition */
    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("role", service.findById(id));
        return "admin/role-form";
    }

    /** Suppression */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        service.delete(id);  // on appelle delete(id), pas deleteById
        return "redirect:/admin/roles";
    }
}
