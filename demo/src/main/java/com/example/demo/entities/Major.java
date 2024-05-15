package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "majors")
public class Major {
    @Id
    @Column(name = "majorID", columnDefinition = "nvarchar(10)")
    private String majorID;
    @Column(name = "name", columnDefinition = "nvarchar(100)")
    private String name;
    @Column(name = "description", columnDefinition = "nvarchar(150)")
    private String description;

    @ManyToOne
    @JoinColumn(name = "deptID")
    private Department department;

    @ManyToMany(mappedBy = "majors",fetch = FetchType.EAGER)
    private List<Course> courses;

    @Override
    public String toString() {
        return "Major{" +
                "majorID='" + majorID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +


                '}';
    }
}
