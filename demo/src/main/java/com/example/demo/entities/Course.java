package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseAFID")
    private Course courseAfter;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_major",
            joinColumns = @JoinColumn(name = "courseID"),
            inverseJoinColumns = @JoinColumn(name = "majorID")
    )
    private List<Major> majors;


    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", type='" + type + '\'' +
                ", courseAfter=" + courseAfter +

                '}';
    }
}
