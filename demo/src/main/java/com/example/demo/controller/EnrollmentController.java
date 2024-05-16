package com.example.demo.controller;

import com.example.demo.dto.EnrollmentDTO;
import com.example.demo.dto.Student_EnrollmentDTO;
import com.example.demo.services.EnrollmentService;
import com.example.demo.services.Student_EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private Student_EnrollmentService student_enrollmentImpl;
    @GetMapping("/dangkyhocphan/lophocphan")
    public List<EnrollmentDTO> getAllEnrollmentByCourseID(@RequestParam("courseID") String courseID){
        return enrollmentService.getAllEnrollmentByCourseID(courseID);
    }
    @GetMapping("/dangkyhocphan/lophocphan/sinhvien")
    public List<EnrollmentDTO> getAllEnrollmentByStudentID(@RequestParam("studentID") String studentID, @RequestParam("semester") int semester, @RequestParam("year") int year){
        return enrollmentService.getAllEnrollmentByStudentID(studentID, semester, year);
    }


    @PostMapping("/dangkyhocphan/lophocphan/sinhvien/dangky")
    public String registerEnrollment(@RequestBody Student_EnrollmentDTO studentEnrollmentDTO) {
        return student_enrollmentImpl.registerEnrollment(studentEnrollmentDTO);

    }
    @GetMapping("/dangkyhocphan/lophocphan/chitietlophocphan")
    public EnrollmentDTO getAllEnrollmentById(@RequestParam("enrollmentID") String enrollmentID){
        return enrollmentService.getAllEnrollmentById(enrollmentID);
    }
}
