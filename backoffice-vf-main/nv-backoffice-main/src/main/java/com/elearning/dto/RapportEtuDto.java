package com.elearning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RapportEtuDto {
    private Long id;

    @NotBlank(message = "Le contenu du rapport est obligatoire")
    @Size(max = 2000, message = "Le rapport ne peut dépasser 2000 caractères")
    private String commentaire;

    public RapportEtuDto() {}

    public RapportEtuDto(Long id, String commentaire) {
        this.id = id;
        this.commentaire = commentaire;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
