package com.example.demo.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @Column(name = "courseID", columnDefinition = "nvarchar(10)")
    private String courseID;
    @Column(name = "name", columnDefinition = "nvarchar(100)")
    private String name;
    @Column(name = "credits")
    private int credits;
    @Column(name = "type", columnDefinition = "nvarchar(50)")
    private String type;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseID")
    private List<Course> prerequisites;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_major",
            joinColumns = @JoinColumn(name = "courseID"),
            inverseJoinColumns = @JoinColumn(name = "majorID")
    )
    private List<Major> majors;
    public Course() {
    }

    public Course(String courseID, String name, int credits, String type) {
        this.courseID = courseID;
        this.name = name;
        this.credits = credits;
        this.type = type;
    }
}
