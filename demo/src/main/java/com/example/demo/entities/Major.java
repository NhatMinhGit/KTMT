package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@ToString
@Getter
@Setter
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
    public Major() {
    }

    public Major(String majorID, String name, String description) {
        this.majorID = majorID;
        this.name = name;
        this.description = description;
    }
}
