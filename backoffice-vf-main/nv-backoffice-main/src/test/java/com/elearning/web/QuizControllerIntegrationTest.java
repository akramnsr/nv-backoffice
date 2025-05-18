// src/test/java/com/elearning/web/QuizControllerIntegrationTest.java
package com.elearning.web;

import com.elearning.model.Quiz;
import com.elearning.model.Role;
import com.elearning.model.User;
import com.elearning.repository.QuizRepository;
import com.elearning.repository.RoleRepository;
import com.elearning.repository.UserRepository;
import com.elearning.repository.RapportEtuRepository;  // <-- Ajoute ceci
import com.elearning.repository.ResultatRepository;    // <-- Si besoin

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuizControllerIntegrationTest extends BaseIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private QuizRepository repo;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private RapportEtuRepository rapportRepo;    // <-- Ajoute ceci
    @Autowired(required = false)
    private ResultatRepository resultatRepo;     // <-- Si tu as Resultat

    @BeforeEach
    void setup() {
        // D'abord, supprimer les rapports et résultats qui référencent des users
        if (resultatRepo != null) resultatRepo.deleteAll(); // Si tu as une table Resultat
        rapportRepo.deleteAll(); // D'abord les enfants

        repo.deleteAll();

        userRepo.deleteAll();
        roleRepo.deleteAll();

        // Création des données de test
        repo.save(new Quiz("Un titre", "Desc", 3, 15, new Date()));
        var etuRole = new Role("ETUDIANT", "étudiant");
        roleRepo.save(etuRole);

        var u = new User();
        u.setEmail("anouasria360@gmail.com");
        u.setMotDePasse(encoder.encode("akram"));
        u.setRole(etuRole);
        userRepo.save(u);
    }

    @Test
    void listPageRenders() {
        String json = rest.getForObject(
                "http://localhost:" + port + "/api/quizzes",
                String.class
        );
        assertThat(json).contains("Un titre");
    }
}
