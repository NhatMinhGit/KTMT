package com.example.demo.controller;

import com.example.demo.dto.ScheduleDTO;
import com.example.demo.dto.ScheduleEnrollmentDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.services.ScheduleService;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentImpl;
    @Autowired
    private ScheduleService scheduleImpl;
    @GetMapping("/students")
    public List<StudentDTO> getAllStudent(){
        List<StudentDTO> students = studentImpl.getAllStudent();
        return students;
    }
    @GetMapping("/students/{studentID}")
    public StudentDTO getStudentById( @PathVariable String studentID){
        return studentImpl.getStudentById(studentID);
    }
    @GetMapping("/students/schedule/{studentID}")
    public List<ScheduleEnrollmentDTO> getStudentSchedule(@PathVariable String studentID){
        return scheduleImpl.getStudentSchedule(studentID);
    }

}
