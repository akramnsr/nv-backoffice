// src/main/java/com/elearning/dto/RoleDto.java
package com.elearning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleDto {

    private Long id;
    private String name;

    public RoleDto() {}
    public RoleDto(Long id, String name) {
        this.id   = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // → la propriété JSON "name"
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // → la propriété JSON "nom"
    @JsonProperty("nom")
    public String getNom() {
        return name;
    }
    @JsonProperty("nom")
    public void setNom(String nom) {
        this.name = nom;
    }
}
