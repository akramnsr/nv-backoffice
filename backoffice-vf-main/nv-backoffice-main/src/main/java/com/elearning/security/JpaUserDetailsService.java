package com.elearning.security;

import com.elearning.model.User;
import com.elearning.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository users;

    public JpaUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = users.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email inconnu : " + email));
        // on transforme votre Role en "ROLE_ADMIN" ou "ROLE_ETUDIANT"
        String roleName = u.getRole() != null ? u.getRole().getNom() : null;
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getMotDePasse(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + roleName)
                ));

    }
}
