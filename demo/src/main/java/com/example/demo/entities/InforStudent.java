package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "inforStudents")
public class InforStudent {

    @Column(name = "scholarship",columnDefinition = "nvarchar(50)",nullable = false)
    private String scholarship;

    @Id
    @OneToOne
    @JoinColumn(name = "id")
    private Student student;
}
