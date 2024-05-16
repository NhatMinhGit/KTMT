package com.example.demo.controller;

import com.example.demo.dto.AccountNormalDTO;
import com.example.demo.services.AccountNormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountNormalController {
    @Autowired
    private AccountNormalService accountNormalService;


    @GetMapping("/accountNormal/{accountID}")
    public AccountNormalDTO getAccountNormalById(@PathVariable String accountID){
        return accountNormalService.getAccountNormalById(accountID);
    }
}
