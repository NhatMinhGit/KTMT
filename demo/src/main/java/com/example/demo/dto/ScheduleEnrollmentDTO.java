package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEnrollmentDTO {
    private List<ScheduleDTO> schedules;
    private String nameCourse;
    private String room;
    private String nameInstructor;
    private String enrollmentID;
    private String nameClass;
    private String note;
}
