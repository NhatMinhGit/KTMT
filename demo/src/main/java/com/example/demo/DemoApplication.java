package com.example.demo;

import com.example.demo.services.CourseService;
import com.example.demo.services.StudentService;
import com.example.demo.services.impl.CourseImpl;
import com.example.demo.services.impl.StudentImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

}
