package com.example.demo.entities;

import lombok.Getter;


@Getter
public enum EnrollmentStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    FINISHED("Finished"),;

    private final String STATUS;

    private EnrollmentStatus(String status) {
        this.STATUS = status;
    }

}