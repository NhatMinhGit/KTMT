package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
public class Student_EnrollmentDTO {
    private String paymentStatus;
    private String studentID;
    private String studyStatus;
    private String note;
    private int processStudy;
    private LocalDate dateApply;
    private String codePractive;
    private String enrollmentID;

    public Student_EnrollmentDTO() {
        this.note= "khong";
        this.paymentStatus = "chua hoan thanh";
        this.studyStatus ="duoc phep thi";
        this.processStudy = 0;
    }
}
