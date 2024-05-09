package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "accountNormals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountNormal extends Account{
    private LocalDate timeLimit;
    private String ruleAccount;
}
