package com.elearning.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    // on ne mettra jamais le mot de passe dans la réponse !
    @GetMapping("/me")
    public Authentication whoAmI(Authentication auth) {
        return auth;
    }
}
