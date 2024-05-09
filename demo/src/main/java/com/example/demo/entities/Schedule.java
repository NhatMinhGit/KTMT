package com.example.demo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduleID")
    private int scheduleID;

    private String dayOfWeek;

    private int classesStart;

    private int classesEnd;

    @ManyToOne
    @JoinColumn(name = "enrollmentID")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "enrollmentPID")
    private EnrollmentP enrollmentP;

    @OneToOne(mappedBy = "exam", fetch = FetchType.EAGER)
    private Enrollment enrollmentExam;

    public Schedule() {
    }

    public Schedule(String dayOfWeek, int classesStart, int classesEnd) {
        this.dayOfWeek = dayOfWeek;
        this.classesStart = classesStart;
        this.classesEnd = classesEnd;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getClassesStart() {
        return classesStart;
    }

    public void setClassesStart(int classesStart) {
        this.classesStart = classesStart;
    }

    public int getClassesEnd() {
        return classesEnd;
    }

    public void setClassesEnd(int classesEnd) {
        this.classesEnd = classesEnd;
    }


    @Override
    public String toString() {
        return "Schedule{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", classesStart=" + classesStart +
                ", classesEnd=" + classesEnd +
                '}';
    }
}
