// src/main/java/com/elearning/model/RapportEtu.java
package com.elearning.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "rapport_etu")
public class RapportEtu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenu;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateSoumission;

    private String commentaireFormateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "etudiant_id")
    private User etudiant;

    public RapportEtu() {}

    public RapportEtu(String contenu, Date dateSoumission, String commentaireFormateur) {
        this.contenu = contenu;
        this.dateSoumission = dateSoumission;
        this.commentaireFormateur = commentaireFormateur;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public Date getDateSoumission() { return dateSoumission; }
    public void setDateSoumission(Date dateSoumission) { this.dateSoumission = dateSoumission; }

    public String getCommentaireFormateur() { return commentaireFormateur; }
    public void setCommentaireFormateur(String commentaireFormateur) {
        this.commentaireFormateur = commentaireFormateur;
    }

    public User getEtudiant() { return etudiant; }
    public void setEtudiant(User etudiant) { this.etudiant = etudiant; }
}
