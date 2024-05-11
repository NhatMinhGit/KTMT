package com.example.demo.dto;

import com.example.demo.entities.Schedule;
import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentPDTO {
    private String enrollmentPID;
    private String name;
    private String room;
    private int quantity;
    private List<Schedule> scheduleStudy;

}
