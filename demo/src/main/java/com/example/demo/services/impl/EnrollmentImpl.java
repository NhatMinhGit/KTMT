package com.example.demo.services.impl;

import com.example.demo.dto.EnrollmentDTO;
import com.example.demo.entities.Enrollment;
import com.example.demo.repositories.EnrollmentRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.Student_EnrollmentRepository;
import com.example.demo.services.EnrollmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentImpl implements EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Student_EnrollmentRepository student_enrollmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Override
    public List<EnrollmentDTO> getAllEnrollmentByCourseID(String courseID) {
        if(!enrollmentRepository.findEnrollmentsByCourse_CourseID(courseID).isEmpty()){
            return enrollmentRepository.findEnrollmentsByCourse_CourseID(courseID).stream().map((element)->{
                String nameInstructor = element.getInstuctor().getName();
                int quantityApply = element.getStudentEnrollments().size();
                EnrollmentDTO enrollmentDTO = modelMapper.map(element, EnrollmentDTO.class);
                enrollmentDTO.setNameInstuctor(nameInstructor);
                enrollmentDTO.setQuantityApply(quantityApply);
                return enrollmentDTO;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollmentByStudentID(String studentID) {
        if (!studentRepository.existsById(studentID)){
            return null;
        }
        if(!student_enrollmentRepository.findStudent_EnrollmentsByStudentStudent(studentRepository.findById(studentID).orElse(null)).isEmpty()){
            return student_enrollmentRepository.findStudent_EnrollmentsByStudentStudent(studentRepository.findById(studentID).orElse(null)).stream().map((element)->{
                Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentID(element.getEnrollment().getEnrollmentID());
                EnrollmentDTO enrollmentDTO = modelMapper.map(enrollment, EnrollmentDTO.class);
                return enrollmentDTO;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public EnrollmentDTO getAllEnrollmentById(String enrollmentID) {
        Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentID(enrollmentID);
        assert enrollment != null;
        EnrollmentDTO enrollmentDTO = modelMapper.map(enrollment, EnrollmentDTO.class);
        return enrollmentDTO;
    }

}
