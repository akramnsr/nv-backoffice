package com.elearning.web;

import com.elearning.dto.FormationDto;
import com.elearning.model.Formation;
import com.elearning.repository.FormationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FormationControllerIntegrationTest {

    @LocalServerPort int port;
    @Autowired FormationRepository repo;
    @Autowired TestRestTemplate rest;

    @MockBean
    private com.elearning.security.JwtFilter jwtFilter;

    @MockBean
    private com.elearning.security.JwtUtil jwtUtil;

    private String base;


    @BeforeEach
    void setup() {
        repo.deleteAll();
        repo.save(new Formation("Java", "Cours Java", 40));
        base = "http://localhost:" + port + "/api/formations";
    }

   /* @Test
    void crudLifecycle() {
        // CREATE
        FormationDto create = new FormationDto(null, "Spring", "Spring Boot");
        ResponseEntity<FormationDto> resPost =
                rest.postForEntity(base, create, FormationDto.class);
        assertThat(resPost.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long id = resPost.getBody().getId();

        // READ
        ResponseEntity<FormationDto> resGet =
                rest.getForEntity(base + "/" + id, FormationDto.class);
        assertThat(resGet.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resGet.getBody().getTitre()).isEqualTo("Spring");

        // UPDATE
        FormationDto update = resGet.getBody();
        update.setDescription("Updated Boot");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FormationDto> entity = new HttpEntity<>(update, headers);
        ResponseEntity<FormationDto> resPut =
                rest.exchange(base + "/" + id, HttpMethod.PUT, entity, FormationDto.class);
        assertThat(resPut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resPut.getBody().getDescription()).isEqualTo("Updated Boot");

        // DELETE
        ResponseEntity<Void> resDel =
                rest.exchange(base + "/" + id, HttpMethod.DELETE, null, Void.class);
        assertThat(resDel.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }*/
}
