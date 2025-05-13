package com.elearning.front.controller;

import com.elearning.model.Resultat;
import com.elearning.service.ResultatService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/resultats")
public class FrontResultatMvcController {

    private final ResultatService service;

    public FrontResultatMvcController(ResultatService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        // Récupère *tous* les résultats sans pagination
        List<Resultat> resultats = service
                .findAll(Pageable.unpaged())
                .getContent();
        model.addAttribute("resultats", resultats);
        return "front/resultat-list";
    }
}
