package com.example.demo.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private String courseID;
    private String name;
    private int credits;
    private String type;
    private List<CourseDTO> prerequisites;


}
