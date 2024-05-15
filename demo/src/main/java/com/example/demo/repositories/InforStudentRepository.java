package com.example.demo.repositories;

import com.example.demo.entities.InforStudent;
import com.example.demo.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InforStudentRepository extends JpaRepository<InforStudent, Student> {
    public InforStudent findById(String id);
}