package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "enrollmentPractives")
public class EnrollmentP {
    @Id
    private String enrollmentPID;
    private String name;
    private String room;
    private int quantity;

    @OneToMany(mappedBy = "enrollmentP",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Schedule> scheduleStudy;
    @ManyToOne
    @JoinColumn(name = "enrollmentID")
    private Enrollment enrollment;



}
