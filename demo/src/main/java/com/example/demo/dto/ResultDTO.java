package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResultDTO {
    private String nameCourse;
    private List<String> regularScore;
    private List<String> practiceScore;
    private double midtermScore;
    private double finalScore;
    private double overallScore;
    private int semester;
    private int year;
    private String enrollmentID;
    private int credit;
    public ResultDTO() {
    }


}
