// src/test/java/com/elearning/web/RoleControllerStandaloneTest.java
package com.elearning.web;

import com.elearning.dto.RoleDto;
import com.elearning.mapper.RoleMapper;
import com.elearning.model.Role;
import com.elearning.rest.RoleRestController;
import com.elearning.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@AutoConfigureMockMvc(addFilters = false)  // DÉSACTIVE LA SÉCURITÉ SPRING
@WebMvcTest(RoleRestController.class)
class RoleControllerStandaloneTest {
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
    void listReturnsPage() throws Exception {
        RoleDto dto = new RoleDto(1L, "ADMIN");
        given(service.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(new Role())));
        given(mapper.toDto(any(Role.class)))
                .willReturn(dto);

        mvc.perform(get("/api/roles")
                        .param("page","0")
                        .param("size","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is(dto.getName())));
    }
}
