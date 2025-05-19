// src/test/java/com/elearning/web/QuizRestControllerTest.java
package com.elearning.web;

import com.elearning.dto.QuizDto;
import com.elearning.mapper.QuizFullMapper;
import com.elearning.mapper.QuizMapper;
import com.elearning.model.Quiz;
import com.elearning.rest.QuizRestController;
import com.elearning.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = QuizRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class QuizRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuizService service;

    @MockBean
    private QuizMapper mapper;

    @MockBean
    private QuizFullMapper fullMapper;

    @MockBean
    private com.elearning.repository.UserRepository userRepository;

    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;

    @MockBean
    private com.elearning.security.JwtUtil jwtUtil;

    @MockBean
    private com.elearning.security.JpaUserDetailsService userDetailsService; // ← INDISPENSABLE POUR TON FILTER

    @MockBean
    private com.elearning.repository.FormationRepository formationRepository;

    @Test
    void listReturnsPage() throws Exception {
        // Créer un Quiz simulé pour le test
        Quiz quiz = new Quiz();
        quiz.setId(42L);
        quiz.setTitre("Mon quiz");
        quiz.setDescription("Desc");

        // Créer un QuizDto simulé pour le test
        QuizDto dto = new QuizDto(42L, "Mon quiz", "Desc");
        given(mapper.toDto(any(Quiz.class))).willReturn(dto);

        // Mock l'appel du service pour renvoyer une page avec un Quiz
        given(service.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(quiz)));

        // Effectuer la requête GET sur /api/quizzes
        mvc.perform(get("/api/quizzes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))  // Vérifie qu'il y a 1 élément
                .andExpect(jsonPath("$.content[0].titre").value("Mon quiz"));  // Vérifie le titre
    }

    // Ajouter d'autres tests si nécessaire
}
