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
    private List<String> regularScore;

    @ElementCollection
    @CollectionTable(name="practiceScores", joinColumns = @JoinColumn(name="resultID"))
    @Column(name="practiceScore", nullable = false)
    private List<String> practiceScore;

    @Column(name = "midtermScore",columnDefinition = "float",nullable = false)
    private double midtermScore;
    @Column(name = "finalScore",columnDefinition = "float",nullable = false)
    private double finalScore;
    @Column(name = "overallScore",columnDefinition = "float",nullable = false)
    private double overallScore;

}
