package com.example.demo.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentPRepository extends JpaRepository<EnrollmentP, String> {
}