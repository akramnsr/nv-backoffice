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

    @Column(nullable = false, length = 2000)
    private String contenu;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date dateSoumission;

    @Column(length = 2000)
    private String commentaireFormateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "etudiant_id")
    private User etudiant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "formation_id")
    private Formation formation;

    public RapportEtu() {}

    // getters / setters

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

    public Formation getFormation() { return formation; }
    public void setFormation(Formation formation) { this.formation = formation; }
}
