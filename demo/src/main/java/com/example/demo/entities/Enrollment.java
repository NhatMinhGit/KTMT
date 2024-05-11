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
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @Column(name = "enrollmentID", nullable = false)
    private String enrollmentID;

    @Column(name = "name",columnDefinition = "nvarchar(50)",nullable = false)
    private String name;
    @Column(name = "year",columnDefinition = "int",nullable = false)
    private int year;
    @Column(name = "semester",columnDefinition = "int",nullable = false)
    private int semester;
    @Column(name = "quantity",columnDefinition = "int",nullable = false)
    private int quantity;
    @Column(name = "roomName",columnDefinition = "nvarchar(50)",nullable = false)
    private String roomName;

    private LocalDate dateStart;
    private LocalDate dateApplyStart;
    private LocalDate dateApplyEnd;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    @OneToMany(mappedBy = "enrollment",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Schedule> scheduleStudy;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "scheduleID", unique = true, nullable = false)
    private Schedule exam;

    @OneToMany(mappedBy = "enrollment",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EnrollmentP> enrollmentPs;

    @OneToMany(mappedBy = "enrollment",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Student_Enrollment> studentEnrollments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CourseID")
    private Course course;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", nullable = false)
    private Instructor instuctor;
}
