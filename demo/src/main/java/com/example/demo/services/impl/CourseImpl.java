package com.example.demo.services.impl;

import com.example.demo.dto.CourseDTO;
import com.example.demo.entities.Course;
import com.example.demo.entities.Major;
import com.example.demo.entities.Student_Enrollment;
import com.example.demo.repositories.*;
import com.example.demo.services.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private Student_EnrollmentRepository student_enrollmentRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<CourseDTO> getAllCoursesOfMajorInSemester(String studentID, int semester, int year) {
        if(studentRepository.findById(studentID).orElse(null)==null){
            return null;
        }
        String majorID = studentRepository.findById(studentID).orElse(null).getMajor().getMajorID();
        Major major = majorRepository.findById(majorID).orElse(null);
        assert major != null;
        List<Course> courses = major.getCourses();
        courses.addAll(majorRepository.findById("CT").orElse(null).getCourses());
        courses.forEach(e->{
            System.out.println(e.getCourseID());
        });
        List<Course> elementsRemove = new ArrayList<>();
        courses.forEach((element)->{
            System.out.println("1"+element.getCourseID());
            enrollmentRepository.findEnrollmentsByCourse_CourseID(element.getCourseID()).forEach((enrollment)->{
                if(enrollment.getSemester()!=semester || enrollment.getYear()!=year){
                    System.out.println("3"+element.getCourseID());
                    elementsRemove.add(element);
                }
            });
            if (enrollmentRepository.findEnrollmentsByCourse_CourseID(element.getCourseID()).isEmpty()){
                System.out.println("4"+element.getCourseID());
                elementsRemove.add(element);
            }
            student_enrollmentRepository.findStudent_EnrollmentsByStudentStudentAndEnrollment_SemesterAndEnrollment_Year(studentRepository.findById(studentID).orElse(null),semester,year).forEach((student_enrollment)->{
                if(student_enrollment.getEnrollment().getCourse().getCourseID().equals(element.getCourseID())){
                    System.out.println("2"+element.getCourseID());
                    elementsRemove.add(element);
                }
            });
        });
        courses.removeAll(elementsRemove);
        return courses.stream().map((element) -> {
            List<Course> perquisites =getPerquisites(element.getCourseID());
            CourseDTO courseDTO = modelMapper.map(element, CourseDTO.class);
            courseDTO.setPrerequisites("");
            if(perquisites==null){
                return courseDTO;
            }
            if(!perquisites.isEmpty()){
                perquisites.forEach((perquisiteCourse)->{
                    if(perquisiteCourse!=null)
                    courseDTO.setPrerequisites(courseDTO.getPrerequisites()+" "+perquisiteCourse.getCourseID()+" ");
                });
            }
            return courseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Course> getPerquisites(String courseID) {
        return courseRepository.findAll().stream().map((element) -> {
            if (element.getCourseAfter() != null) {
                if (element.getCourseAfter().getCourseID().equals(courseID))
                    return element;
                return null;
            } else {
                return null;
            }
        }).collect(Collectors.toList());

    }
}
