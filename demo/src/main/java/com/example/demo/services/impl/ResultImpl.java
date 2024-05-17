package com.example.demo.services.impl;

import com.example.demo.dto.ResultDTO;
import com.example.demo.entities.Enrollment;
import com.example.demo.repositories.EnrollmentRepository;
import com.example.demo.repositories.ResultRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.Student_EnrollmentRepository;
import com.example.demo.services.ResultService;
import com.example.demo.services.Student_EnrollmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultImpl implements ResultService {
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Student_EnrollmentRepository student_enrollmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Override
    public List<ResultDTO> getResultByStudentID(String studentID) {
        return student_enrollmentRepository.findStudent_EnrollmentsByStudentStudent(studentRepository.findStudentById(studentID)).stream().map((student_enrollment) ->{
            Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentID(student_enrollment.getEnrollment().getEnrollmentID());
            String nameCourse = enrollment.getCourse().getName();
            int semester = enrollment.getSemester();
            int year = enrollment.getYear();
            int credit = enrollment.getCourse().getCredits();
            ResultDTO resultDTO= modelMapper.map(student_enrollment.getResult(), ResultDTO.class);
            resultDTO.setNameCourse(nameCourse);
            resultDTO.setSemester(semester);
            resultDTO.setYear(year);
            resultDTO.setCredit(credit);
            resultDTO.setEnrollmentID(student_enrollment.getEnrollment().getEnrollmentID());
            return resultDTO;
        }).toList();

    }
}
