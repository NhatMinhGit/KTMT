package com.example.demo.services.impl;

import com.example.demo.dto.Student_EnrollmentDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import com.example.demo.services.CourseService;
import com.example.demo.services.ScheduleService;
import com.example.demo.services.Student_EnrollmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class Student_EnrollmentImpl implements Student_EnrollmentService {
    @Autowired
    private Student_EnrollmentRepository student_enrollmentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InforStudentRepository inforStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private CourseService courseImpl;
    @Autowired
    private ScheduleService scheduleImpl;
    @Autowired
    private EnrollmentPRepository enrollmentPRepository;
    @Override
    public String registerEnrollment(Student_EnrollmentDTO studentEnrollmentDTO) {
        // check so luong sinh vien dang ky
        Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentID(studentEnrollmentDTO.getEnrollmentID());
        if(enrollment.getStudentEnrollments().size()==enrollment.getQuantity()){
            return "Đã đủ số lượng sinh viên đăng ký";
        }
        // check đieu kien tien quyet
        Student student = studentRepository.findById(studentEnrollmentDTO.getStudentID()).orElse(null);
        AtomicInteger flag = new AtomicInteger();

        student_enrollmentRepository.findStudent_EnrollmentsByStudentStudent(student).forEach((element)->{
           if(!checkPrerequisite(element.getStudent().getId(),element.getEnrollment().getCourse().getCourseID())){
               flag.set(1);
               return;
           }
        });
        if(flag.get()==1){
            return "Không đủ điều kiện tien quyet";
        }
        //check trung lich
        Schedule schedule = checkSchedule(studentEnrollmentDTO.getStudentID(),studentEnrollmentDTO.getEnrollmentID(),studentEnrollmentDTO.getCodePractive());
        if(schedule!=null){
            return "Trùng lịch học với lich " + schedule.getDayOfWeek() + " từ " + schedule.getClassesStart() + " đến " + schedule.getClassesEnd();
        }

        Student_Enrollment student_enrollment = modelMapper.map(studentEnrollmentDTO, Student_Enrollment.class);
        student_enrollment.setEnrollment(enrollmentRepository.findEnrollmentByEnrollmentID(studentEnrollmentDTO.getEnrollmentID()));
        student_enrollment.setStudent(inforStudentRepository.findById(studentEnrollmentDTO.getStudentID()));
        Result rs = new Result();
        rs.setResultID(student_enrollment.getStudent().getId()+"-"+student_enrollment.getEnrollment().getEnrollmentID());
        student_enrollment.createResult(rs);
        resultRepository.save(rs);
        Student_Enrollment saveS = student_enrollmentRepository.save(student_enrollment);
        return "Đăng ký thành công";
    }

    @Override
    public boolean checkPrerequisite(String studentID, String courseID) {
        Student student = studentRepository.findById(studentID).orElse(null);
        AtomicInteger flag = new AtomicInteger();

            courseImpl.getPerquisites(courseID).forEach((element1)->{
                if (element1==null){
                    flag.set(1);
                    return;
                }else {
                    student_enrollmentRepository.findStudent_EnrollmentsByStudentStudent(student).forEach((element2)->{
                        if (element2==null){
                            flag.set(1);
                            return;
                        }
                        if(element2.getEnrollment().getCourse().getCourseID().equals(element1.getCourseID())){
                            flag.set(1);
                            return;
                        }
                    });
                }

            });

        if (flag.get()==1){
            return true;
        }
        return false;
    }

    @Override
    public Schedule checkSchedule(String studentID, String EnrollmentID, String enrollmentPID) {
        AtomicReference<Schedule> schedule = new AtomicReference<>();
        Student student = studentRepository.findById(studentID).orElse(null);
        List<Student_Enrollment> student_enrollments = student_enrollmentRepository.findStudent_EnrollmentsByStudentStudent(student);
        if (student_enrollments.isEmpty()){
            return schedule.get();
        }
        Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentID(EnrollmentID);
        student_enrollments.forEach((element)->{
            element.getEnrollment().getScheduleStudy().forEach((element1)->{
                enrollment.getScheduleStudy().forEach((element2)->{
                    if(!scheduleImpl.checkSchedule(element1, element2)){
                        System.out.println(element2);
                        schedule.set(element1);
                        return;
                    }
                });
                if (enrollmentPID.equals("")){
                    return;
                }
                EnrollmentP enrollmentP = enrollmentPRepository.findById(enrollmentPID).orElse(null);
                assert enrollmentP != null;
                if(!scheduleImpl.checkSchedule(enrollmentP.getScheduleStudy(),element1)){
                    System.out.println(enrollmentP.getScheduleStudy());
                        schedule.set(element1);
                        return;
                    }


            });
            if(element.getCodePractive().trim().equals("")){
                return;
            }
            EnrollmentP enrollmentP = enrollmentPRepository.findById(element.getCodePractive()).orElse(null);
                enrollment.getScheduleStudy().forEach((element2)->{
                    assert enrollmentP != null;
                    if (enrollmentP.getScheduleStudy()==null){
                        return;
                    }
                    if(!scheduleImpl.checkSchedule(enrollmentP.getScheduleStudy(),element2)){
                        schedule.set(enrollmentP.getScheduleStudy());
                        System.out.println("3");
                        return;
                    }
                });
            EnrollmentP enrollmentPDky = enrollmentPRepository.findById(enrollmentPID).orElse(null);
            assert enrollmentPDky != null;
            assert enrollmentP != null;
            if (enrollmentPDky.getScheduleStudy()==null){
                return;
            }

            if(!scheduleImpl.checkSchedule(enrollmentPDky.getScheduleStudy(),enrollmentP.getScheduleStudy())){
                schedule.set(enrollmentP.getScheduleStudy());
                System.out.println("4");
                return;
            }

        });
        return schedule.get();
    }


}


