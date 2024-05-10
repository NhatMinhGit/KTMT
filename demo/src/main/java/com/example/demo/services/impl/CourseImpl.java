package com.example.demo.services.impl;

import com.example.demo.dto.CourseDTO;
import com.example.demo.entities.Course;
import com.example.demo.entities.Major;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.MajorRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        courses.addAll(majorRepository.findById("2").orElse(null).getCourses());
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
                    courseDTO.setPrerequisites( courseDTO.getPrerequisites() + perquisiteCourse.getCourseID() + " "+ perquisiteCourse.getName() + " ");
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
