package com.elearning.admin.controller;

import com.elearning.model.Formation;
import com.elearning.model.Quiz;
import com.elearning.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final FormationRepository formationRepo;
    private final QuizRepository quizRepo;
    private final UserRepository userRepo;
    private final RapportEtuRepository rapportRepo;
    private final ResultatRepository resultatRepo;

    public DashboardController(FormationRepository formationRepo,
                               QuizRepository quizRepo,
                               UserRepository userRepo,
                               RapportEtuRepository rapportRepo,
                               ResultatRepository resultatRepo) {
        this.formationRepo = formationRepo;
        this.quizRepo      = quizRepo;
        this.userRepo      = userRepo;
        this.rapportRepo   = rapportRepo;
        this.resultatRepo  = resultatRepo;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        // Comptages globaux
        model.addAttribute("formationCount", formationRepo.count());
        model.addAttribute("quizCount",      quizRepo.count());
        model.addAttribute("userCount",      userRepo.count());
        model.addAttribute("rapportCount",   rapportRepo.count());
        model.addAttribute("resultatCount",  resultatRepo.count());

        // 5 dernières formations et quizzes
        List<Formation> recentFormations = formationRepo
                .findAll(PageRequest.of(0, 5, Sort.by("id").descending()))
                .getContent();
        List<Quiz> recentQuizzes = quizRepo
                .findAll(PageRequest.of(0, 5, Sort.by("id").descending()))
                .getContent();

        model.addAttribute("recentFormations", recentFormations);
        model.addAttribute("recentQuizzes",    recentQuizzes);

        return "admin/dashboard";
    }
}
