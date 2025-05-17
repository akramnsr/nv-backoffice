// src/main/java/com/elearning/service/RoleService.java
package com.elearning.service;

import com.elearning.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService {
    Page<Role> findAll(Pageable pageable);
    Optional<Role> findById(Long id);
    Role     save(Role role);
    void     delete(Long id);
}
