package com.example.demo.services;

import com.example.demo.dto.StudentDTO;
import com.example.demo.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentDTO> getAllStudent();
    StudentDTO getStudentById(String studentID);
    void addStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(String studentID);
}
