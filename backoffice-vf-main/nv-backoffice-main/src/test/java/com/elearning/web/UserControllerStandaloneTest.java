package com.elearning.web;

import com.elearning.dto.UserDto;
import com.elearning.mapper.UserMapper;
import com.elearning.model.User;
import com.elearning.rest.UserRestController;
import com.elearning.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserRestController.class)
class UserControllerStandaloneTest {
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
    private UserService service;

    @MockBean
    private UserMapper mapper;


    @Test
    void listReturnsPage() throws Exception {
        UserDto dto = new UserDto(1L, "Dupont", "Jean", "jean.dupont@example.com", new Date(), "USER");
        given(service.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(new User())));
        given(mapper.toDto(any(User.class)))
                .willReturn(dto);

        mvc.perform(get("/api/users")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].nom", is(dto.getNom())))
                .andExpect(jsonPath("$.content[0].prenom", is(dto.getPrenom())))
                .andExpect(jsonPath("$.content[0].email", is(dto.getEmail())));
    }
}
