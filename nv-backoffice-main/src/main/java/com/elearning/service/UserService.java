// src/main/java/com/elearning/service/UserService.java
package com.elearning.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.elearning.model.User;
import java.util.Optional;

public interface UserService {
    Page<User> findAll(Pageable pageable);
    Optional<User>     findById(Long id);
    User     save(User user);
    void     delete(Long id);

    // nouvelle méthode pour la sécurité
    Optional<User> findByEmail(String email);
}
