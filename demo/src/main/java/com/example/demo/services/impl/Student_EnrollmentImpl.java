package com.example.demo.services.impl;

import com.example.demo.dto.Student_EnrollmentDTO;
import com.example.demo.entities.Result;
import com.example.demo.entities.Student_Enrollment;
import com.example.demo.repositories.*;
import com.example.demo.services.EnrollmentService;
import com.example.demo.services.StudentService;
import com.example.demo.services.Student_EnrollmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Student_EnrollmentImpl implements Student_EnrollmentService {
    @Autowired
    private Student_EnrollmentRepository student_enrollmentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InforStudentRepository inforStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Override
    public boolean registerEnrollment(Student_EnrollmentDTO studentEnrollmentDTO) {
        Student_Enrollment student_enrollment = modelMapper.map(studentEnrollmentDTO, Student_Enrollment.class);
        student_enrollment.setEnrollment(enrollmentRepository.findEnrollmentByEnrollmentID(studentEnrollmentDTO.getEnrollmentID()));
        student_enrollment.setStudent(inforStudentRepository.findById(studentEnrollmentDTO.getStudentID()));
        Result rs = new Result();
        rs.setResultID(student_enrollment.getStudent().getId()+"-"+student_enrollment.getEnrollment().getEnrollmentID());
        student_enrollment.createResult(rs);
        resultRepository.save(rs);
        Student_Enrollment saveS = student_enrollmentRepository.save(student_enrollment);
        return true;
    }
}
