// src/main/java/com/elearning/dto/QuestionDto.java
package com.elearning.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class QuestionDto {
    private Long id;

    @NotBlank @Size(max=1000)
    private String libelle;

    private Long quizId;

    /** texts of the four choices */
    private List<ChoiceDto> choices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public List<ChoiceDto> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceDto> choices) {
        this.choices = choices;
    }
}
