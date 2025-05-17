// src/main/java/com/elearning/service/impl/RoleServiceImpl.java
package com.elearning.service.impl;

import com.elearning.model.Role;
import com.elearning.repository.RoleRepository;
import com.elearning.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repo;

    public RoleServiceImpl(RoleRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return Optional.ofNullable(repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Rôle non trouvé pour l'ID " + id)));
    }

    @Override
    public Role save(Role role) {
        return repo.save(role);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException(
                    "Impossible de supprimer : Rôle introuvable pour l'ID " + id);
        }
        repo.deleteById(id);
    }
}
