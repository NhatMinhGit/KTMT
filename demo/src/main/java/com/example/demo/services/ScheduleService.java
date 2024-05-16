package com.example.demo.services;

import com.example.demo.entities.Schedule;
import org.springframework.stereotype.Service;


public interface ScheduleService {
    public boolean checkSchedule(Schedule schedule1,Schedule schedule2);
}
