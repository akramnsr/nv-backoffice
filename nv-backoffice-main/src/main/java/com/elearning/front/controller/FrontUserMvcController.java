package com.elearning.front.controller;

import com.elearning.model.User;
import com.elearning.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class FrontUserMvcController {

    private final UserService service;

    public FrontUserMvcController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model, Pageable pageable) {
        // si vous voulez simplement tout récupérer sans pagination :
        var page = service.findAll(pageable);
        model.addAttribute("users", page.getContent());
        model.addAttribute("page", page);
        return "front/user-list";
    }
}
