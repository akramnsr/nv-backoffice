// src/main/java/com/elearning/model/Video.java
package com.elearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;   // nom du fichier stocké
    private String url;        // URL publique (ex. "/uploads/videos/xxx.mp4")

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Formation getFormation() { return formation; }
    public void setFormation(Formation formation) { this.formation = formation; }
}
