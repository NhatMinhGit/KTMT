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
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "scholarship",columnDefinition = "nvarchar(50)",nullable = false)
    private String scholarship;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id",referencedColumnName = "id")
    private Student student;
}
