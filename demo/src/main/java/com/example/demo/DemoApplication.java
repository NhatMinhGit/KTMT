package com.example.demo;

import com.example.demo.services.CourseService;
import com.example.demo.services.StudentService;
import com.example.demo.services.impl.CourseImpl;
import com.example.demo.services.impl.StudentImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.demo.entities")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

}
