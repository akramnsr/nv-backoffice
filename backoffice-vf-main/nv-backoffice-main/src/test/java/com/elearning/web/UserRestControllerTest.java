package com.elearning.web;

import com.elearning.dto.UserDto;
import com.elearning.mapper.UserMapper;
import com.elearning.model.Role;
import com.elearning.model.User;
import com.elearning.rest.UserRestController;
import com.elearning.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {
    @MockBean
    private com.elearning.repository.UserRepository userRepository;

    @MockBean
    private com.elearning.repository.FormationRepository formationRepository;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;

    @MockBean
    private com.elearning.security.JwtUtil jwtUtil;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;


    @Test
    @DisplayName("GET /api/users renvoie bien une page de DTO")
    void listReturnsPage() throws Exception {
        UserDto dto = new UserDto(2L, "Martin", "Alice", "alice.martin@example.com", new Date(), "ADMIN");
        User entity = new User();
        entity.setId(2L);
        entity.setNom("Martin");
        entity.setPrenom("Alice");
        entity.setEmail("alice.martin@example.com");
        entity.setDateInscription(dto.getDateInscription());
        entity.setRole(new Role("ADMIN", ""));

        Page<User> page = new PageImpl<>(List.of(entity), PageRequest.of(0, 10), 1);

        given(service.findAll(any(Pageable.class))).willReturn(page);
        given(mapper.toDto(any(User.class))).willReturn(dto);

        mvc.perform(get("/api/users")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].nom").value(dto.getNom()));
    }

    @Test
    @DisplayName("GET /api/users/{id} renvoie un seul DTO")
    void getOneReturnsDto() throws Exception {
        UserDto dto = new UserDto(3L, "Lefevre", "Paul", "paul.lefevre@example.com", new Date(), "USER");
        User entity = new User();
        entity.setId(3L);
        entity.setNom("Lefevre");
        entity.setPrenom("Paul");
        entity.setEmail("paul.lefevre@example.com");
        entity.setDateInscription(dto.getDateInscription());
        entity.setRole(new Role("USER", ""));

        given(service.findById(3L)).willReturn(Optional.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        mvc.perform(get("/api/users/{id}", 3L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.nom").value(dto.getNom()))
                .andExpect(jsonPath("$.prenom").value(dto.getPrenom()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()));
    }
}
