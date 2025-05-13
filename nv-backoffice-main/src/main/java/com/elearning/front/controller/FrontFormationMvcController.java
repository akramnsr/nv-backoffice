package com.elearning.front.controller;

import com.elearning.model.Formation;
import com.elearning.service.FormationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/formations")
public class FrontFormationMvcController {

    private final FormationService service;

    public FrontFormationMvcController(FormationService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        // on récupère tout sans pagination
        List<Formation> formations = service
                .findAll(Pageable.unpaged())
                .getContent();
        model.addAttribute("formations", formations);
        return "front/formation-list";  // sans slash initial
    }
}
