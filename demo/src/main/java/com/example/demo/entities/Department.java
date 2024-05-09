package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "departments")
public class Department {
    @Id
    @Column(name = "deptID", columnDefinition = "nvarchar(10)")
    private String deptID;
    @Column(name = "deptName", columnDefinition = "nvarchar(50)")
    private String deptName;
    @Column(name = "date", columnDefinition = "date")
    private LocalDate date;
    @Column(name = "description", columnDefinition = "nvarchar(150)")
    private String description;

    @Embedded
    private Contact contact;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deanID", unique = true, nullable = true)
    private Instructor dean;

    public Department() {
    }

    public Department(String deptID, String deptName, LocalDate date, String description) {
        this.deptID = deptID;
        this.deptName = deptName;
        this.date = date;
        this.description = description;
    }


}
