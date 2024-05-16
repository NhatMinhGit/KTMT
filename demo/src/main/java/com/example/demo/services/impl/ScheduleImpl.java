package com.example.demo.services.impl;

import com.example.demo.entities.Schedule;
import com.example.demo.services.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class ScheduleImpl implements ScheduleService {
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
}
