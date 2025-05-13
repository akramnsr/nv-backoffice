package com.elearning.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Table(name = "resultat")
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float score;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date datePassage;

    private String statut;

    // ← liaison obligatoire vers un étudiant
    @ManyToOne(optional = false)
    @JoinColumn(name = "etudiant_id", nullable = false)
    private User etudiant;

    public Resultat() {}

    // getters & setters...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public float getScore() { return score; }
    public void setScore(float score) { this.score = score; }

    public Date getDatePassage() { return datePassage; }
    public void setDatePassage(Date datePassage) { this.datePassage = datePassage; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public User getEtudiant() { return etudiant; }
    public void setEtudiant(User etudiant) { this.etudiant = etudiant; }

}
