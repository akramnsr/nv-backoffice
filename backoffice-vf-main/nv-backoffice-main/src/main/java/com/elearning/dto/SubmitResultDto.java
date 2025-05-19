package com.elearning.dto;

public class SubmitResultDto {
    private Long quizId;
    private Double score;

    // Getters / Setters
    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
}
