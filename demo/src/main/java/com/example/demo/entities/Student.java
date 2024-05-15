package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "id")
public class Student extends People {
    private int academicYear;
    private int status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clazzID")
    private Clazz clazz;

}

