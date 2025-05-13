package com.elearning.model;

import com.elearning.dto.FormationDto;
import jakarta.persistence.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


@Entity
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private int dureeHeures;

    public Formation(String titre, String description, int dureeHeures) {
        this.titre = titre;
        this.description = description;
        this.dureeHeures = dureeHeures;
    }


    public Formation() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDureeHeures() {
        return dureeHeures;
    }

    public void setDureeHeures(int dureeHeures) {
        this.dureeHeures = dureeHeures;
    }


}
