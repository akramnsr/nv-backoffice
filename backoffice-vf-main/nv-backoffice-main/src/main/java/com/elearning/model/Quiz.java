package com.elearning.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private int nbQuestions;
    private int duree;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // <-- permet le binding de "2025-05-24"
    private Date dateInscription;



    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;


    public Quiz() {}

    public Quiz(String titre, String description, int nbQuestions, int duree, Date dateInscription) {
        this.titre = titre;
        this.description = description;
        this.nbQuestions = nbQuestions;
        this.duree = duree;
        this.dateInscription = dateInscription;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }

    @JsonAlias({"titre", "title"})
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getNbQuestions() { return nbQuestions; }

    public void setNbQuestions(int nbQuestions) { this.nbQuestions = nbQuestions; }

    public int getDuree() { return duree; }

    public void setDuree(int duree) { this.duree = duree; }

    public Date getDateInscription() { return dateInscription; }

    public void setDateInscription(Date dateInscription) { this.dateInscription = dateInscription; }

    public Formation getFormation() { return formation; }

    public void setFormation(Formation formation) { this.formation = formation; }



}