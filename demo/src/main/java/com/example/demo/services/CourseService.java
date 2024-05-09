package com.example.demo.services;

import com.example.demo.dto.CourseDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCoursesOfStudentById(String id, String semester);
}
