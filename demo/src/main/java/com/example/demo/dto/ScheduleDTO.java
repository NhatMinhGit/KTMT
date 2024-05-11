package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private String dayOfWeek;

    private int classesStart;

    private int classesEnd;
}
