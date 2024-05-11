package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "studentEnrollment")
@IdClass(Student_EnrollmentID.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student_Enrollment that = (Student_Enrollment) o;
        return Objects.equals(student, that.student) && Objects.equals(enrollment, that.enrollment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, enrollment);
    }
}
