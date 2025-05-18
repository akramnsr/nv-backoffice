// src/test/java/com/elearning/web/AdminAuthTest.java
package com.elearning.web;

import com.elearning.model.Role;
import com.elearning.model.User;
import com.elearning.repository.RoleRepository;
import com.elearning.repository.UserRepository;
import com.elearning.repository.RapportEtuRepository; // à importer
import com.elearning.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdminAuthTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private RapportEtuRepository rapportRepo; // <--- AJOUTÉ

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder encoder;

    @BeforeEach
    void setup() {
        // 1. Supprimer les dépendances qui référencent les users (avant userRepo.deleteAll)
        rapportRepo.deleteAll();    // <--- AJOUTÉ

        // 2. Ensuite seulement, supprimer les users et rôles
        userRepo.deleteAll();
        roleRepo.deleteAll();

        // 3. Recréer rôle et admin
        Role adminRole = new Role("ADMIN", "Administrateur");
        roleRepo.save(adminRole);

        User admin = new User();
        admin.setNom("nouasria");
        admin.setPrenom("akram");
        admin.setEmail("anouasria360@gmail.com");
        admin.setMotDePasse(encoder.encode("akram"));
        admin.setRole(adminRole);
        admin.setDateInscription(new Date());
        userRepo.save(admin);
    }

    @Test
    void accessAdminWithJwtToken() {
        // on génère le token avec le rôle ADMIN
        String token = jwtUtil.generateToken("anouasria360@gmail.com", "ADMIN");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = rest.exchange(
                "http://localhost:" + port + "/admin/formations",
                HttpMethod.GET,
                entity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("formations");
    }
}
