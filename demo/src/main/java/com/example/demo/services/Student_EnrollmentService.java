package com.example.demo.services;

import com.example.demo.dto.Student_EnrollmentDTO;
import org.springframework.stereotype.Service;

public interface Student_EnrollmentService {
    boolean registerEnrollment(Student_EnrollmentDTO studentEnrollmentDTO);
}
