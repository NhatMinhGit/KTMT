package com.example.demo.services.impl;

import com.example.demo.dto.ScheduleDTO;
import com.example.demo.dto.ScheduleEnrollmentDTO;
import com.example.demo.entities.Enrollment;
import com.example.demo.entities.EnrollmentP;
import com.example.demo.entities.Schedule;
import com.example.demo.repositories.*;
import com.example.demo.services.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
public class ScheduleImpl implements ScheduleService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private Student_EnrollmentRepository student_enrollmentRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EnrollmentPRepository enrollmentPRepository;
    @Override
    public boolean checkSchedule(Schedule schedule1, Schedule schedule2) {
        if(!Objects.equals(schedule1.getDayOfWeek(), schedule2.getDayOfWeek())){
            return true;
        }else{
            if(schedule1.getClassesEnd()<schedule2.getClassesStart()){
                System.out.println("a");
                return true;
            } else if (schedule1.getClassesStart()>schedule2.getClassesEnd()){
                System.out.println("b");
                return true;
            } else{
                return false;
            }
        }
    }

    @Override
    public List<ScheduleEnrollmentDTO> getStudentSchedule(String studentID) {
        return student_enrollmentRepository.findStudent_EnrollmentsByStudentStudent(studentRepository.findStudentById(studentID)).stream().map((student_enrollment) -> {
            Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentID(student_enrollment.getEnrollment().getEnrollmentID());
            ScheduleEnrollmentDTO scheduleEnrollmentDTO = new ScheduleEnrollmentDTO();
            scheduleEnrollmentDTO.setEnrollmentID(student_enrollment.getEnrollment().getEnrollmentID());
            scheduleEnrollmentDTO.setNameCourse(enrollment.getCourse().getName());
            scheduleEnrollmentDTO.setNameClass(enrollment.getName());
            scheduleEnrollmentDTO.setRoom(enrollment.getRoomName());
            scheduleEnrollmentDTO.setNameInstructor(enrollment.getInstuctor().getName());
            List<ScheduleDTO> scheduleDTOS = new ArrayList<>(enrollment.getScheduleStudy().stream().map((schedule) -> {
                return modelMapper.map(schedule, ScheduleDTO.class);
            }).toList());
            if(!student_enrollment.getCodePractive().equals("")){
                EnrollmentP enrollmentP = enrollmentPRepository.findById(student_enrollment.getCodePractive()).orElse(null);
              Schedule sPractice = enrollmentP.getScheduleStudy();
                ScheduleDTO scheduleDTO = modelMapper.map(sPractice, ScheduleDTO.class);
                scheduleDTO.setIsPractice("Practice");
                scheduleDTO.setNameInstructor(enrollmentP.getInstructor().getName());
                scheduleDTOS.add(scheduleDTO);

            }
            scheduleEnrollmentDTO.setSchedules(scheduleDTOS);
            return scheduleEnrollmentDTO;
        }).toList();
    }
}
