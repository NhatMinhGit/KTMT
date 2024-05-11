package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class AccountNormalDTO {
    private String username;
    private String password;
    private LocalDate timeLimit;
    private String ruleAccount;

    public AccountNormalDTO() {
    }

    public AccountNormalDTO(String username, String password, LocalDate timeLimit, String ruleAccount) {
        this.username = username;
        this.password = password;
        this.timeLimit = timeLimit;
        this.ruleAccount = ruleAccount;
    }
}
