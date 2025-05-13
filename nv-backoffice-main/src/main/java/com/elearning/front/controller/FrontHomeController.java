package com.elearning.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")      // ou "/public", selon ce que vous voulez exposer
public class FrontHomeController {

    @GetMapping
    public String home() {
        return "home";
    }

    // Exemple : page catalogue, détails, login…
    @GetMapping("/catalogue")
    public String catalogue() {
        return "catalogue";
    }
}
