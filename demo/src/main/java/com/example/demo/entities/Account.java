package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "username", columnDefinition = "nvarchar(20)", nullable = false)
    protected String username;
    @Column(name = "password", columnDefinition = "nvarchar(20)", nullable = false)
    protected String password;
}
