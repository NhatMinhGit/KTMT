package com.example.demo.services;

import com.example.demo.dto.ResultDTO;

import java.util.List;

public interface ResultService {
    public List<ResultDTO> getResultByStudentID(String studentID);

}
