package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "studentEnrollment")
public class Student_Enrollment {
    @Column(name = "paymentStatus",columnDefinition = "nvarchar(20)",nullable = false)
    private String paymentStatus;
    @Column(name = "studyStatus",columnDefinition = "nvarchar(20)",nullable = false)
    private String studyStatus;
    @Column(name = "noteStudentEnrollment",columnDefinition = "nvarchar(50)",nullable = false)
    private String note;
    @Column(name = "processStudy",columnDefinition = "int",nullable = false)
    private int processStudy;
    private LocalDate dateApply;
    private String codePractive;
    @Id
    @ManyToOne
    @JoinColumn(name = "studentID")
    private InforStudent student;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resultID", columnDefinition = "nvarchar(20)", nullable = false)
    private Result result;

    @Id
    @ManyToOne
    @JoinColumn(name = "enrollmentID")
    private Enrollment enrollment;
}
