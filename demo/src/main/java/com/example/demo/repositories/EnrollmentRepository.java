package com.example.demo.repositories;

import com.example.demo.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    List<Enrollment> findEnrollmentsByCourse_CourseID(String courseID);
    Enrollment findEnrollmentByEnrollmentID(String enrollmentID);
}