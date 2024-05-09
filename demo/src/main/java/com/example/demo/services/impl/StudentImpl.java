package com.example.demo.services.impl;

import com.example.demo.dto.StudentDTO;
import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;
import com.example.demo.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<StudentDTO> getAllStudent() {

        return studentRepository.findAll().stream().map((element) -> {
            String nameMajor = element.getMajor().getName();
            String nameClazz = element.getClazz().getClazzName();
           StudentDTO studentDTO = modelMapper.map(element, StudentDTO.class);
            studentDTO.setNameMajor(nameMajor);
            studentDTO.setNameClazz(nameClazz);
            return studentDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(String studentID) {
        Student student = studentRepository.findById(studentID).orElse(null);
        assert student != null;
        String nameMajor = student.getMajor().getName();
        String nameClazz = student.getClazz().getClazzName();
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        studentDTO.setNameMajor(nameMajor);
        studentDTO.setNameClazz(nameClazz);
        return studentDTO;

    }

    @Override
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(String studentID) {
        studentRepository.deleteById(studentID);
    }
}
