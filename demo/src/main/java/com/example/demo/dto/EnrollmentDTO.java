package com.example.demo.dto;

import com.example.demo.entities.EnrollmentP;
import com.example.demo.entities.EnrollmentStatus;
import com.example.demo.entities.Schedule;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.SecondaryTable;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private String enrollmentID;
    private String name;
    private int year;
    private int semester;
    private int quantity;
    private int quantityApply;
    private String roomName;
    private LocalDate dateStart;
    private LocalDate dateApplyStart;
    private LocalDate dateApplyEnd;

    private EnrollmentStatus status;
    private List<Schedule> scheduleStudy;
    private List<EnrollmentPDTO> enrollmentPs;
    private String nameInstuctor;


}
