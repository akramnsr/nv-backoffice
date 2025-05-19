package com.elearning.web;

import com.elearning.dto.RoleDto;
import com.elearning.mapper.RoleMapper;
import com.elearning.model.Role;
import com.elearning.rest.RoleRestController;
import com.elearning.service.RoleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RoleRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class RoleRestControllerTest {

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
    private RoleService service;

    @MockBean
    private RoleMapper mapper;

    @Test
    @DisplayName("GET /api/roles renvoie bien une page de DTO")
    void listReturnsPage() throws Exception {
        RoleDto dto = new RoleDto(2L, "USER");
        Role entity = new Role();
        entity.setId(2);
        entity.setNom("USER");

        Page<Role> page = new PageImpl<>(List.of(entity), PageRequest.of(0, 10), 1);

        given(service.findAll(any(Pageable.class))).willReturn(page);
        given(mapper.toDto(any(Role.class))).willReturn(dto);

        mvc.perform(get("/api/roles")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].nom").value(dto.getName()));
    }

    @Test
    @DisplayName("GET /api/roles/{id} renvoie un seul DTO")
    void getOneReturnsDto() throws Exception {
        RoleDto dto = new RoleDto(3L, "MANAGER");
        Role entity = new Role();
        entity.setId(3);
        entity.setNom("MANAGER");

        given(service.findById(3L)).willReturn(Optional.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        mvc.perform(get("/api/roles/{id}", 3L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.nom").value(dto.getName()));
    }
}
