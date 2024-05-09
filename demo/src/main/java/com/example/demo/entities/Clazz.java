package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "classes")
public class Clazz {
    @Id
    private String clazzID;
    private String clazzName;
    private int quantity;
    private String discription;
    @OneToOne
    @JoinColumn(name = "id")
    private Instructor instructor;
}
