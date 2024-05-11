package com.example.demo.repositories;

import com.example.demo.entities.InforStudent;
import com.example.demo.entities.Student;
import com.example.demo.entities.Student_Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Student_EnrollmentRepository extends JpaRepository<Student_Enrollment, InforStudent> {
    List<Student_Enrollment> findStudent_EnrollmentsByStudentStudent(Student student);
}