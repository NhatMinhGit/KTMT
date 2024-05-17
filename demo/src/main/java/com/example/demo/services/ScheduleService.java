package com.example.demo.services;

import com.example.demo.dto.ScheduleDTO;
import com.example.demo.dto.ScheduleEnrollmentDTO;
import com.example.demo.entities.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ScheduleService {
    public boolean checkSchedule(Schedule schedule1,Schedule schedule2);
    public List<ScheduleEnrollmentDTO> getStudentSchedule(String studentID);
}
