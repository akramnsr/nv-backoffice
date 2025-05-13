package com.elearning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormationDto {
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 100, message = "Le titre ne peut dépasser 100 caractères")
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @JsonProperty("dureeHeures")
    private int dureeHeures;

    public FormationDto() {}
    public FormationDto(Long id, String titre, String description, int dureeHeures) {
        this.id = id; this.titre = titre; this.description = description;
        this.dureeHeures = dureeHeures;

    }


    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDureeHeures() { return dureeHeures; }
    public void setDureeHeures(int dureeHeures) { this.dureeHeures = dureeHeures; }
}
