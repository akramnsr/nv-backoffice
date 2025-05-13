package com.elearning.front.controller;

import com.elearning.model.RapportEtu;
import com.elearning.repository.RapportEtuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rapports-etu")
public class FrontRapportEtuMvcController {

    private final RapportEtuRepository repo;

    public FrontRapportEtuMvcController(RapportEtuRepository repo) {
        this.repo = repo;
    }

    // 1. Liste
    @GetMapping
    public String list(Model model) {
        model.addAttribute("rapports", repo.findAll());
        return "/front/rapports-etu-list";
    }


}
