// src/test/java/com/elearning/web/RapportEtuControllerStandaloneTest.java
package com.elearning.web;

import com.elearning.dto.RapportEtuDto;
import com.elearning.mapper.RapportEtuMapper;
import com.elearning.model.RapportEtu;
import com.elearning.rest.RapportEtuRestController;
import com.elearning.service.RapportEtuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(RapportEtuRestController.class)
class RapportEtuControllerStandaloneTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;

    @MockBean
    private com.elearning.repository.UserRepository userRepository;

    @MockBean
    private com.elearning.repository.FormationRepository formationRepository;

    @MockBean
    private com.elearning.repository.RapportEtuRepository rapportEtuRepository; // <-- AJOUTE CECI !

    @MockBean
    private com.elearning.security.JwtUtil jwtUtil;

    @MockBean
    private RapportEtuService service;

    @MockBean
    private RapportEtuMapper mapper;

    @Test
    void listReturnsPage() throws Exception {
        RapportEtuDto dto = new RapportEtuDto(1L, "Commentaire test");

        given(service.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(new RapportEtu()), Pageable.ofSize(1), 1));
        given(mapper.toDto(any(RapportEtu.class)))
                .willReturn(dto);

        mvc.perform(get("/api/rapports")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].commentaire").value(dto.getCommentaire()));
    }

    @Test
    void getOneReturnsDto() throws Exception {
        RapportEtuDto dto = new RapportEtuDto(2L, "Un autre commentaire");
        RapportEtu entity = new RapportEtu();
        entity.setId(2L);

        given(service.findById(2L)).willReturn(Optional.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        mvc.perform(get("/api/rapports/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.commentaire").value(dto.getCommentaire()));
    }
}
