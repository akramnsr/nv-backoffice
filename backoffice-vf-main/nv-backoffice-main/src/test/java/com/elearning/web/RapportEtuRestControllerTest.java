package com.elearning.web;

import com.elearning.dto.RapportEtuDto;
import com.elearning.mapper.RapportEtuMapper;
import com.elearning.model.RapportEtu;
import com.elearning.rest.RapportEtuRestController;
import com.elearning.service.RapportEtuService;
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

@WebMvcTest(controllers = RapportEtuRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class RapportEtuRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private com.elearning.repository.UserRepository userRepository;
    @MockBean
    private com.elearning.repository.RapportEtuRepository rapportEtuRepository; // AJOUT ICI 👈

    @MockBean
    private com.elearning.repository.FormationRepository formationRepository;
    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;
    @MockBean
    private com.elearning.security.JwtUtil jwtUtil;
    @MockBean
    private RapportEtuService service;
    @MockBean
    private RapportEtuMapper mapper;

    @Test
    @DisplayName("GET /api/rapports renvoie bien une page de DTO")
    void listReturnsPage() throws Exception {
        RapportEtuDto dto = new RapportEtuDto(10L, "Rapport #10");
        RapportEtu entity = new RapportEtu();
        entity.setId(10L);

        Page<RapportEtu> page = new PageImpl<>(
                List.of(entity),
                PageRequest.of(0, 10, Sort.by("id")),
                1
        );

        given(service.findAll(any(Pageable.class))).willReturn(page);
        given(mapper.toDto(any(RapportEtu.class))).willReturn(dto);

        mvc.perform(get("/api/rapports")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].commentaire").value(dto.getCommentaire()));
    }

    @Test
    @DisplayName("GET /api/rapports/{id} renvoie un seul DTO")
    void getOneReturnsDto() throws Exception {
        RapportEtuDto dto = new RapportEtuDto(7L, "Rapport #7");
        RapportEtu entity = new RapportEtu();
        entity.setId(7L);

        given(service.findById(7L)).willReturn(Optional.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        mvc.perform(get("/api/rapports/{id}", 7L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.commentaire").value(dto.getCommentaire()));
    }
}
