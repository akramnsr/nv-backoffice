// src/main/java/com/elearning/model/Formation.java
package com.elearning.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "formation")
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private int dureeHeures;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Video> videos = new ArrayList<>();


    @OneToMany(
            mappedBy = "formation",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER   // charge les quizzes pour getNombreQuiz()
    )
    private List<Quiz> quizzes = new ArrayList<>();



    public Formation() { }

    public Formation(String titre, String description, int dureeHeures) {
        this.titre = titre;
        this.description = description;
        this.dureeHeures = dureeHeures;
    }

    // ————— Getters / Setters standards —————

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDureeHeures() { return dureeHeures; }
    public void setDureeHeures(int dureeHeures) { this.dureeHeures = dureeHeures; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<Quiz> getQuizzes() { return quizzes; }
    public void setQuizzes(List<Quiz> quizzes) { this.quizzes = quizzes; }
    public List<Video> getVideos() { return videos; }
    public void setVideos(List<Video> videos) { this.videos = videos; }
    /**
     * Calculé à la volée, non persistant
     */
    @Transient
    public int getNombreQuiz() {
        return quizzes.size();
    }

    }
