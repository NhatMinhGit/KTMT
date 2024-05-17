package com.example.demo.repositories;

import com.example.demo.entities.EnrollmentP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentPRepository extends JpaRepository<EnrollmentP, String> {
}