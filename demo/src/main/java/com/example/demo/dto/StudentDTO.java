package com.example.demo.dto;

import com.example.demo.entities.AccountNormal;
import com.example.demo.entities.Clazz;
import com.example.demo.entities.Contact;
import com.example.demo.entities.Major;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StudentDTO {
    private String id;
    private String name;
    private int age;
    private boolean sex;
    private String cccd;
    private String nationality;
    private String img;
    private Contact contact;
    private String nameMajor;
    private int academicYear;
    private int status;
    private String nameClazz;
    public StudentDTO(String id, String name, int age, boolean sex, String cccd, String nationality, String img, Contact contact, String nameMajor, int academicYear, int status, String nameClazz) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.cccd = cccd;
        this.nationality = nationality;
        this.img = img;
        this.contact = contact;
        this.nameMajor = nameMajor;
        this.academicYear = academicYear;
        this.status= status;
        this.nameClazz = nameClazz;
    }

    public StudentDTO() {
    }


}
