// src/main/java/com/elearning/dto/UserDto.java
package com.elearning.dto;

import java.util.Date;

public class UserDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Date dateInscription;
    private String roleName;

    public UserDto() {}

    public UserDto(Long id, String nom, String prenom, String email, Date dateInscription, String roleName) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateInscription = dateInscription;
        this.roleName = roleName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getDateInscription() { return dateInscription; }
    public void setDateInscription(Date dateInscription) { this.dateInscription = dateInscription; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
