package com.example.demo.services.impl;

import com.example.demo.dto.EnrollmentDTO;
import com.example.demo.entities.Enrollment;
import com.example.demo.model.Caculator;
import com.example.demo.repositories.EnrollmentRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.Student_EnrollmentRepository;
import com.example.demo.services.EnrollmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
    public List<EnrollmentDTO> getAllEnrollmentByStudentID(String studentID, int semester, int year) {
        if (!studentRepository.existsById(studentID)){
            return null;
        }
        if(!student_enrollmentRepository.findStudent_EnrollmentsByStudentStudentAndEnrollment_SemesterAndEnrollment_Year(studentRepository.findById(studentID).orElse(null),semester,year).isEmpty()){
            return student_enrollmentRepository.findStudent_EnrollmentsByStudentStudentAndEnrollment_SemesterAndEnrollment_Year(studentRepository.findById(studentID).orElse(null),semester,year).stream().map((element)->{
                Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentID(element.getEnrollment().getEnrollmentID());

                EnrollmentDTO enrollmentDTO = modelMapper.map(enrollment, EnrollmentDTO.class);
                enrollmentDTO.setCredit(enrollment.getCourse().getCredits());
                enrollmentDTO.setNameCourse(enrollment.getCourse().getName());
                enrollmentDTO.setCodePractice(element.getCodePractive());
                enrollmentDTO.genPaymentDeadline();
                enrollmentDTO.setFee(Caculator.caculatorFee(enrollment.getCourse().getCredits()));
                enrollmentDTO.setPaymentStatus(element.getPaymentStatus());
                enrollmentDTO.setDateApply(element.getDateApply());
                return enrollmentDTO;
            }).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public EnrollmentDTO getAllEnrollmentById(String enrollmentID) {
        Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentID(enrollmentID);
        List<String> nameInstutor = enrollment.getEnrollmentPs().stream().map((element)->{
            return element.getInstructor().getName();
        }).toList();

        EnrollmentDTO enrollmentDTO = modelMapper.map(enrollment, EnrollmentDTO.class);
        AtomicInteger flag = new AtomicInteger();
        enrollmentDTO.getEnrollmentPs().forEach((element)->{
            element.setNameInstructor(nameInstutor.get(flag.get()));
            flag.getAndIncrement();
        });
        return enrollmentDTO;
    }

}
