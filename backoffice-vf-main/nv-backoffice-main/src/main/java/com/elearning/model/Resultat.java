// src/main/java/com/elearning/model/Resultat.java
package com.elearning.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "resultat")
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double score;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date datePassage;

    @Column(length = 50)
    private String statut;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "etudiant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User etudiant;

    public Resultat() {}

    public Resultat(Double score, Date datePassage, String statut, User etudiant) {
        this.score = score;
        this.datePassage = datePassage;
        this.statut = statut;
        this.etudiant = etudiant;
    }

    // --- getters & setters ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }

    public Date getDatePassage() {
        return datePassage;
    }
    public void setDatePassage(Date datePassage) {
        this.datePassage = datePassage;
    }

    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }

    public User getEtudiant() {
        return etudiant;
    }
    public void setEtudiant(User etudiant) {
        this.etudiant = etudiant;
    }
}
