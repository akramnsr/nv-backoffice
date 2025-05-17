package com.elearning.admin.controller;

import com.elearning.service.FormationService;
import com.elearning.service.QuizService;
import com.elearning.service.RapportEtuService;
import com.elearning.service.ResultatService;
import com.elearning.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final UserService userService;
    private final FormationService formationService;
    private final QuizService quizService;
    private final RapportEtuService rapportService;
    private final ResultatService resultatService;

    public AdminDashboardController(UserService userService,
                                    FormationService formationService,
                                    QuizService quizService,
                                    RapportEtuService rapportService,
                                    ResultatService resultatService) {
        this.userService       = userService;
        this.formationService  = formationService;
        this.quizService       = quizService;
        this.rapportService    = rapportService;
        this.resultatService   = resultatService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalAdmins",
                userService.countByRoleName("ADMIN"));
        model.addAttribute("totalEtudiants",
                userService.countByRoleName("ETUDIANT"));
        model.addAttribute("totalFormations",
                formationService.countAll());
        model.addAttribute("totalQuizzes",
                quizService.countAll());
        model.addAttribute("totalRapports",
                rapportService.countAll());
        model.addAttribute("totalResultats",
                resultatService.countAll());
        return "admin/dashboard";
    }
}
