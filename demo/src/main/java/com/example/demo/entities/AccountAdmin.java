package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "accountAdmins")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class AccountAdmin extends Account{
    public AccountAdmin(String username, String password) {
        super(username, password);
    }
    private String ip;


}
