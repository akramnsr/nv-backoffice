// src/test/java/com/elearning/web/FormationRestControllerIntegrationTest.java
package com.elearning.web;

import com.elearning.dto.FormationDto;
import com.elearning.model.Formation;
import com.elearning.repository.FormationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FormationRestControllerIntegrationTest extends BaseIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FormationRepository repo;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/formations";
    }

    @Test
    void getOneReturnsDto() {
        // 1) On persiste une formation
        Formation f = new Formation("Java", "Cours Java", 40);
        f = repo.saveAndFlush(f);

        // 2) On appelle l’API SANS authentification (GET public)
        ResponseEntity<FormationDto> res =
                restTemplate.getForEntity(baseUrl() + "/" + f.getId(), FormationDto.class);

        // 3) Assertions
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isNotNull();
        assertThat(res.getBody().getId()).isEqualTo(f.getId());
        assertThat(res.getBody().getTitre()).isEqualTo("Java");
        assertThat(res.getBody().getDureeHeures()).isEqualTo(40);
    }
}
