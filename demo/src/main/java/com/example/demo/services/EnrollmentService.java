package com.example.demo.services;

import com.example.demo.dto.EnrollmentDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EnrollmentService {
List<EnrollmentDTO> getAllEnrollmentByCourseID(String courseID);
    EnrollmentDTO getAllEnrollmentById(String enrollmentID);
List<EnrollmentDTO> getAllEnrollmentByStudentID(String studentID, int semester, int year);
}
