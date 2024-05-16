package com.example.demo.repositories;

import com.example.demo.entities.Enrollment;
import com.example.demo.entities.InforStudent;
import com.example.demo.entities.Student;
import com.example.demo.entities.Student_Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface Student_EnrollmentRepository extends JpaRepository<Student_Enrollment, InforStudent> {
    List<Student_Enrollment> findStudent_EnrollmentsByStudentStudentAndEnrollment_SemesterAndEnrollment_Year(Student student, int semester, int year);
    List<Student_Enrollment> findStudent_EnrollmentsByStudentStudent(Student student);
}