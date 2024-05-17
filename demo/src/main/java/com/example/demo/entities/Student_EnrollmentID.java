package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Student_EnrollmentID implements Serializable {
    private InforStudent student;
    private Enrollment enrollment;
}
