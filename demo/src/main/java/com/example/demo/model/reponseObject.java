package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class reponseObject {
    private String status;
    private String message;
    private Object data;

    public reponseObject(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
