package com.example.demo.services;

import com.example.demo.dto.EnrollmentDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EnrollmentService {
List<EnrollmentDTO> getAllEnrollmentByCourseID(String courseID);
List<EnrollmentDTO> getAllEnrollmentByStudentID(String studentID);
    EnrollmentDTO getAllEnrollmentById(String enrollmentID);
}
