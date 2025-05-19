// src/test/java/com/elearning/web/FormationRestControllerTest.java
package com.elearning.web;

import com.elearning.dto.FormationDto;
import com.elearning.mapper.FormationMapperImpl;
import com.elearning.model.Formation;
import com.elearning.rest.FormationRestController;
import com.elearning.service.FormationService;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(FormationRestController.class)
class FormationRestControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private com.elearning.repository.FormationRepository formationRepository;  // <--- AJOUTE CETTE LIGNE
    @MockBean
    private com.elearning.repository.UserRepository userRepository;


    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;

    @MockBean
    private com.elearning.security.JwtUtil jwtUtil;

    @MockBean
    private FormationService service;

    @MockBean
    private FormationMapperImpl mapper;


    @Test
    @DisplayName("GET /api/formations renvoie une page de DTO")
    void listReturnsPage() throws Exception {
        FormationDto dto = new FormationDto(100L, "Java Avancé", "Deep dive Spring",12,"url");
        Formation entity = new Formation();
        entity.setId(100L);
        entity.setTitre("Java Avancé");
        entity.setDescription("Deep dive Spring");

        Page<Formation> pageEntities =
                new PageImpl<>(List.of(entity), PageRequest.of(0, 10), 1);

        given(service.findAll(any(Pageable.class))).willReturn(pageEntities);
        given(mapper.toDto(any(Formation.class))).willReturn(dto);

        mvc.perform(get("/api/formations")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].titre").value(dto.getTitre()));
    }

    @Test
    @DisplayName("GET /api/formations/{id} renvoie un seul DTO")
    void getOneReturnsDto() throws Exception {
        FormationDto dto = new FormationDto(7L, "React Intro", "Bases React",12,"url");
        Formation entity = new Formation();
        entity.setId(7L);
        entity.setTitre("React Intro");
        entity.setDescription("Bases React");

        given(service.findById(7L)).willReturn(Optional.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        mvc.perform(get("/api/formations/{id}", 7L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.titre").value(dto.getTitre()));
    }
}
