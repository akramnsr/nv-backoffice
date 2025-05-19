package com.elearning.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class FormationDto {

    private Long id;

    @NotBlank
    private String titre;

    @NotBlank
    private String description;

    @NotNull @Min(0)
    private int dureeHeures;

    private List<MultipartFile> videoFiles;

    private List<String> videoUrls;
    private List<VideoDto> videos = new ArrayList<>();


    /** Contiendra le nom du fichier image (ex. "photo.png") */
    private String imageUrl;

    public FormationDto() {}

    public FormationDto(Long id, String titre, String description,
                        Integer dureeHeures, String imageUrl) {
        this.id           = id;
        this.titre        = titre;
        this.description  = description;
        this.dureeHeures  = dureeHeures;
        this.imageUrl     = imageUrl;
    }


    // ————— Getters / Setters —————

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDureeHeures() { return dureeHeures; }
    public void setDureeHeures(Integer dureeHeures) { this.dureeHeures = dureeHeures; }


    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<MultipartFile> getVideoFiles() { return videoFiles; }
    public void setVideoFiles(List<MultipartFile> videoFiles) { this.videoFiles = videoFiles; }

    public List<String> getVideoUrls() { return videoUrls; }
    public void setVideoUrls(List<String> videoUrls) { this.videoUrls = videoUrls; }
    public List<VideoDto> getVideos() { return videos; }
    public void setVideos(List<VideoDto> videos) { this.videos = videos; }

}
