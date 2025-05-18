package com.elearning.api;

import com.elearning.model.Role;
import com.elearning.model.User;
import com.elearning.repository.RoleRepository;
import com.elearning.repository.UserRepository;
import com.elearning.security.JpaUserDetailsService;
import com.elearning.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JpaUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ✅ Injection par CONSTRUCTEUR
    public AuthApiController(
            UserRepository userRepository,
            RoleRepository roleRepository,
            JpaUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getUsername(), "ETUDIANT");
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Cet email existe déjà !"));
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getPassword()));
        user.setNom(request.getNom());      // <==== Ajouté
        user.setPrenom(request.getPrenom()); // <==== Ajouté

        Role etudiantRole = roleRepository.findByNom("ETUDIANT")
                .orElseThrow(() -> new RuntimeException("Rôle ETUDIANT introuvable"));
        user.setRole(etudiantRole);

        userRepository.save(user);

        // Génère le token direct
        String token = jwtUtil.generateToken(user.getEmail(), "ETUDIANT");

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }


    // DTO internes pour login/signup
    public static class LoginRequest {
        private String email;
        private String password;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class SignupRequest {
        private String email;
        private String password;
        private String nom;     // <==== Ajouté
        private String prenom;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }
    }
}
