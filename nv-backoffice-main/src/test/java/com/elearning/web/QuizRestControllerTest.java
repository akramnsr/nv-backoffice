// src/test/java/com/elearning/web/QuizRestControllerTest.java
package com.elearning.web;

import com.elearning.dto.QuizDto;
import com.elearning.mapper.QuizMapper;
import com.elearning.model.Quiz;
import com.elearning.rest.QuizRestController;
import com.elearning.service.QuizService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// src/test/java/com/elearning/web/QuizRestControllerTest.java
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
    private com.elearning.security.JwtUtil jwtUtil;

    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;

    // *nouveau* mock pour satisfaire la dépendance de JwtFilter
    @MockBean
    private com.elearning.security.JpaUserDetailsService userDetailsService;

    @Test
    void listReturnsPage() throws Exception {
        QuizDto dto = new QuizDto(42L, "Mon quiz", "Desc");
        given(service.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(new Quiz())));
        given(mapper.toDto(any(Quiz.class))).willReturn(dto);

        mvc.perform(get("/api/quizzes")
                        .param("page","0")
                        .param("size","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].titre").value("Mon quiz"));
    }

    // … idem pour le test getOne() …
}
