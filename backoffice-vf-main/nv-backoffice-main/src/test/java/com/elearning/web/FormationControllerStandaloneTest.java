// src/test/java/com/elearning/web/FormationControllerStandaloneTest.java
package com.elearning.web;

import com.elearning.dto.FormationDto;
import com.elearning.mapper.FormationMapperImpl;
import com.elearning.model.Formation;
import com.elearning.rest.FormationRestController;
import com.elearning.service.FormationService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)  // DÉSACTIVE LA SÉCURITÉ SPRING
@WebMvcTest(controllers = FormationRestController.class)
class FormationControllerStandaloneTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;
    @MockBean
    private com.elearning.repository.UserRepository userRepository;  // <--- AJOUTE CETTE LIGNE

    @MockBean
    private com.elearning.repository.FormationRepository formationRepository;  // <--- AJOUTE CETTE LIGNE

    @MockBean
    private com.elearning.security.JwtUtil jwtUtil;

    @MockBean
    private FormationService service;

    @MockBean
    private FormationMapperImpl mapper;

    @Test
    void listReturnsPage() throws Exception {
        FormationDto dto = new FormationDto(55L, "Docker", "Conteneurs",12,"url");

        given(service.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(new Formation())));
        given(mapper.toDto(any(Formation.class)))
                .willReturn(dto);

        mvc.perform(get("/api/formations?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].titre", is(dto.getTitre())));
    }
}
