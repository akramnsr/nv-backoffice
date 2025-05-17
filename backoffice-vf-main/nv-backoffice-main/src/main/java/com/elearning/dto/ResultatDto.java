package com.elearning.dto;

import jakarta.validation.constraints.NotNull;

public class ResultatDto {

    private Long id;

    @NotNull(message = "Le score est obligatoire")
    private Double score;

    public ResultatDto() {}

    public ResultatDto(Long id, Double score) {
        this.id    = id;
        this.score = score;
    }

    public long getId() {
        return Math.toIntExact(id);
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
}
