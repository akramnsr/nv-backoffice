package com.elearning.controller;

import com.elearning.model.User;
import com.elearning.repository.UserRepository;
import com.elearning.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepo,
                          PasswordEncoder encoder,
                          JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder  = encoder;
        this.jwtUtil  = jwtUtil;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpServletResponse response) {

        Optional<User> opt = userRepo.findByEmail(email);
        if (opt.isPresent()
                && encoder.matches(password, opt.get().getMotDePasse())
                && "ADMIN".equalsIgnoreCase(opt.get().getRole().getNom())) {

            String token = jwtUtil.generateToken(email, "ADMIN");
            Cookie c = new Cookie("admin_jwt", token);
            c.setHttpOnly(true);
            c.setPath("/");
            c.setMaxAge(3600);
            response.addCookie(c);
            return "redirect:/admin/dashboard";
        }

        return "redirect:/admin/login?error";
    }

    @GetMapping("/logout")
    public String doLogout(HttpServletResponse response) {
        Cookie c = new Cookie("admin_jwt", "");
        c.setMaxAge(0);
        c.setPath("/");
        response.addCookie(c);
        return "redirect:/admin/login?logout";
    }
}
