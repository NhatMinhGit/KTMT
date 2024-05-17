package com.example.demo.repositories;

import com.example.demo.entities.Enrollment;
import com.example.demo.entities.InforStudent;
import com.example.demo.entities.Student;
import com.example.demo.entities.Student_Enrollment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface Student_EnrollmentRepository extends JpaRepository<Student_Enrollment, InforStudent> {
    public List<Student_Enrollment> findStudent_EnrollmentsByStudentStudentAndEnrollment_SemesterAndEnrollment_Year(Student student, int semester, int year);
    public List<Student_Enrollment> findStudent_EnrollmentsByStudentStudent(Student student);
    public Student_Enrollment findStudent_EnrollmentByStudentStudentAndEnrollment(Student student, Enrollment enrollment);
    public void deleteStudent_EnrollmentByEnrollmentAndStudent(Enrollment enrollment, InforStudent student);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM student_enrollment WHERE enrollmentid = :enrollmentId AND studentid = :studentId", nativeQuery = true)
    void deleteByEnrollmentIdAndStudentId(@Param("enrollmentId") String enrollmentId, @Param("studentId") String studentId);
}