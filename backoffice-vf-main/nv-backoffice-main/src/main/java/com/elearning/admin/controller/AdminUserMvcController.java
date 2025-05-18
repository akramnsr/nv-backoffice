// src/main/java/com/elearning/admin/controller/AdminUserMvcController.java
package com.elearning.admin.controller;

import com.elearning.model.Role;
import com.elearning.model.User;
import com.elearning.service.RoleService;
import com.elearning.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserMvcController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminUserMvcController(UserService userService,
                                  RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /** 1. Tous les utilisateurs */
    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("users",
                userService.findAll(Pageable.unpaged()).getContent());
        return "admin/user-list";
    }

    /** 2. Seulement les admins */
    @GetMapping("/admins")
    public String listAdmins(Model model) {
        model.addAttribute("users",
                userService.findAllByRoleName("ADMIN"));
        return "admin/user-list";
    }

    /** 3. Seulement les étudiants */
    @GetMapping("/etudiants")
    public String listEtudiants(Model model) {
        model.addAttribute("users",
                userService.findAllByRoleName("ETUDIANT"));
        return "admin/user-list";
    }

    /** 4. Formulaire de création */
    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles",
                roleService.findAll(Pageable.unpaged()).getContent());
        return "admin/user-form";
    }

    /** 5. Création */
    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult binding,
                         @RequestParam("role.id") Long roleId,
                         Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("roles",
                    roleService.findAll(Pageable.unpaged()).getContent());
            return "admin/user-form";
        }
        Role r = roleService.findById(roleId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Rôle introuvable : " + roleId));
        user.setRole(r);  // ✅ CORRECT !
        userService.save(user);
        return "redirect:/admin/users/etudiants";
    }

    /** 6. Formulaire d’édition */
    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        User u = userService.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Utilisateur introuvable : " + id));
        model.addAttribute("user", u);
        model.addAttribute("roles",
                roleService.findAll(Pageable.unpaged()).getContent());
        return "admin/user-form";
    }

    /** 7. Mise à jour */
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult binding,
                         @RequestParam("role.id") Long roleId,
                         Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("roles",
                    roleService.findAll(Pageable.unpaged()).getContent());
            return "admin/user-form";
        }
        Role r = roleService.findById(roleId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Rôle introuvable : " + roleId));
        user.setId(id);
        user.setRole(r);  // ✅ CORRECT !
        userService.save(user);
        return "redirect:/admin/users";
    }

    /** 8. Suppression */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    /** 9. Profil de l’admin connecté */
    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Utilisateur introuvable : " + email));
        model.addAttribute("user", user);
        return "admin/user-profile";
    }


}
