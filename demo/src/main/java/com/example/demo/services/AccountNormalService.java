package com.example.demo.services;

import com.example.demo.dto.AccountNormalDTO;

import java.util.List;

public interface AccountNormalService {
    List<AccountNormalDTO> getAllAccountNormal();
    AccountNormalDTO getAccountNormalById(String accountNormalID);
}
