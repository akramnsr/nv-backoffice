// src/main/java/com/elearning/service/UserService.java
package com.elearning.service;

import com.elearning.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> findAll(Pageable pageable);
    Optional<User> findById(Long id);
    User save(User user);
    void delete(Long id);

    /** Dashboard admin */
    long countByRoleName(String roleName);

    /** Auth & filtres */
    Optional<User> findByEmail(String email);
    List<User> findAllByRoleName(String roleName);

}
