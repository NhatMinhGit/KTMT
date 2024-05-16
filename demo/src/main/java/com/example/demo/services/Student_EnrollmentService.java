package com.example.demo.services;

import com.example.demo.dto.Student_EnrollmentDTO;
import com.example.demo.entities.Schedule;
import org.springframework.stereotype.Service;

public interface Student_EnrollmentService {
    String registerEnrollment(Student_EnrollmentDTO studentEnrollmentDTO);
    boolean checkPrerequisite(String studentID, String courseID);
    Schedule checkSchedule(String studentID, String EnrollCourseID, String EnrollmentPID);
}
