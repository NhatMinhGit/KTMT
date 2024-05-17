package com.example.demo.controller;

import com.example.demo.dto.ResultDTO;
import com.example.demo.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResultController {
    @Autowired
    private ResultService resultImpl;
    @GetMapping("sinhvien/result/{studentID}")
    public List<ResultDTO> getResultByStudentID(@PathVariable String studentID) {
        return resultImpl.getResultByStudentID(studentID);
    }
}
