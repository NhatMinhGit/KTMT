package com.example.demo.controller;

import com.example.demo.dto.CourseDTO;
import com.example.demo.services.CourseService;
import com.example.demo.services.impl.CourseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseImpl;
    @GetMapping("/dangkyhocphan")
    public List<CourseDTO> getAllCourseByMajorAndSemester(@RequestParam("studentID") String studentID,
                                                          @RequestParam("semester") int semester,
                                                          @RequestParam("year") int year){

        List<CourseDTO> courseDTOS = courseImpl.getAllCoursesOfMajorInSemester(studentID, semester, year);
        courseDTOS.forEach(System.out::println);
        return courseDTOS;
    }
}
