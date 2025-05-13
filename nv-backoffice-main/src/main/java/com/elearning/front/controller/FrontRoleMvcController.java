package com.elearning.front.controller;

import com.elearning.model.Role;
import com.elearning.service.RoleService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class FrontRoleMvcController {

    private final RoleService service;

    public FrontRoleMvcController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        // Récupère tous les rôles sans pagination
        List<Role> roles = service
                .findAll(Pageable.unpaged())
                .getContent();
        model.addAttribute("roles", roles);
        return "front/role-list";    // sans slash devant
    }
}
