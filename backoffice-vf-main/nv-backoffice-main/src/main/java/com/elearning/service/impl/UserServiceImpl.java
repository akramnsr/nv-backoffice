// src/main/java/com/elearning/service/impl/UserServiceImpl.java
package com.elearning.service.impl;

import com.elearning.model.User;
import com.elearning.repository.UserRepository;
import com.elearning.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.of(repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Utilisateur non trouvé pour l'ID " + id)));
    }

    @Override
    public User save(User user) {
        return repo.save(user);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : utilisateur " + id + " inexistant");
        }
        repo.deleteById(id);
    }

    @Override
    public long countByRoleName(String roleName) {
        return repo.countByRoleNom(roleName);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public List<User> findAllByRoleName(String roleName) {
        return repo.findAllByRoleNom(roleName);
    }




}
