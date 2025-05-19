package com.elearning.web;

import com.elearning.dto.ResultatDto;
import com.elearning.mapper.ResultatMapper;
import com.elearning.model.Resultat;
import com.elearning.repository.UserRepository;
import com.elearning.rest.ResultatRestController;
import com.elearning.service.ResultatService;
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

@WebMvcTest(controllers = ResultatRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class ResultatRestControllerTest {

    @MockBean
    private com.elearning.repository.UserRepository userRepository;

    @MockBean
    private com.elearning.repository.FormationRepository formationRepository;

    @MockBean
    private com.elearning.repository.QuizRepository quizRepository;

    @MockBean
    private com.elearning.repository.ResultatRepository resultatRepository;

    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;

    @MockBean
    private com.elearning.security.JwtUtil jwtUtil;

    @MockBean
    private ResultatService service;

    @MockBean
    private ResultatMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("GET /api/resultats renvoie bien une page de DTO")
    void listReturnsPage() throws Exception {
        ResultatDto dto = new ResultatDto(10L, 15.0);
        Resultat entity = new Resultat();
        entity.setId(10L);

        Page<Resultat> page = new PageImpl<>(
                List.of(entity),
                PageRequest.of(0, 10, Sort.by("id")),
                1
        );

        given(service.findAll(any(Pageable.class))).willReturn(page);
        given(mapper.toDto(any(Resultat.class))).willReturn(dto);

        mvc.perform(get("/api/resultats")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].score").value(dto.getScore()));
    }

    @Test
    @DisplayName("GET /api/resultats/{id} renvoie un seul DTO")
    void getOneReturnsDto() throws Exception {
        ResultatDto dto = new ResultatDto(7L, 9.5);
        Resultat entity = new Resultat();
        entity.setId(7L);

        given(service.findById(7L)).willReturn(Optional.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        mvc.perform(get("/api/resultats/{id}", 7L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.score").value(dto.getScore()));
    }
}
