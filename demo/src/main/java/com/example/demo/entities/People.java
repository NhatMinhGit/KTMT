package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "peoples")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class People {
    @Id
    @Column(name = "id", nullable = false)
    protected String id;
    protected String name;
    protected int age;
    protected boolean sex;
    protected String cccd;
    protected String nationality;
    protected String img;

    @Embedded
    protected Contact contact;

    @ManyToOne
    @JoinColumn(name = "majorID", nullable = false)
    protected Major major;

    @OneToOne
    @JoinColumn(name = "accountID", unique = true, nullable = false)
    protected AccountNormal accountNormal;
}
