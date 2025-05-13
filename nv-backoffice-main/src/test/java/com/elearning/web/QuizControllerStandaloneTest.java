// src/test/java/com/elearning/web/QuizControllerStandaloneTest.java
package com.elearning.web;

import com.elearning.dto.QuizDto;
import com.elearning.mapper.QuizMapper;
import com.elearning.model.Quiz;
import com.elearning.rest.QuizRestController;
import com.elearning.service.QuizService;
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

@AutoConfigureMockMvc(addFilters = false)   // on désactive Spring Security ici
@WebMvcTest(controllers = QuizRestController.class)
class QuizControllerStandaloneTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;

    @MockBean
    private com.elearning.security.JwtUtil jwtUtil;

    @MockBean
    private QuizService service;

    @MockBean
    private QuizMapper mapper;

    @Test
    void listReturnsPage() throws Exception {
        // 1) Préparer un DTO
        QuizDto dto = new QuizDto(42L, "Mon super quiz", "Une description");

        // 2) Mocker service.findAll(Pageable) + mapper.toDto(...)
        given(service.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(new Quiz())));
        given(mapper.toDto(any(Quiz.class)))
                .willReturn(dto);

        // 3) Appel GET et assertions JSON
        mvc.perform(get("/api/quizzes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].titre", is(dto.getTitre())));
    }
}
