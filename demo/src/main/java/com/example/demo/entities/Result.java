package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "results")
public class Result {
    @Id
    @Column(name = "resultID",columnDefinition = "nvarchar(20)",nullable = false)
    private String resultID;

    @ElementCollection
    @CollectionTable(name="regularScore", joinColumns = @JoinColumn(name="resultID"))
    @Column(name="regularScore", nullable = false)
    private List<Double> regularScore;

    @ElementCollection
    @CollectionTable(name="practiceScores", joinColumns = @JoinColumn(name="resultID"))
    @Column(name="practiceScore")
    private List<Double> practiceScore;

    @Column(name = "midtermScore",columnDefinition = "float")
    private double midtermScore;
    @Column(name = "finalScore",columnDefinition = "float")
    private double finalScore;
    @Column(name = "overallScore",columnDefinition = "float")
    private double overallScore;
    public void calculatorOverallScore(){
        double sum = 0;
        for (Double score: regularScore){
            sum += score;
        }
        this.overallScore = sum*0.3+midtermScore*0.2 + finalScore*0.5;
    }
}
