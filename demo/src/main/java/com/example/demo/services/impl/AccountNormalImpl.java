package com.example.demo.services.impl;

import com.example.demo.dto.AccountNormalDTO;
import com.example.demo.entities.AccountNormal;
import com.example.demo.repositories.AccountNormalRepository;
import com.example.demo.services.AccountNormalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountNormalImpl implements AccountNormalService {
    @Autowired
    private AccountNormalRepository accountNormalRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<AccountNormalDTO> getAllAccountNormal() {
        return accountNormalRepository.findAll().stream().map((element) -> {
            AccountNormalDTO accountNormalDTO = modelMapper.map(element, AccountNormalDTO.class);
            return accountNormalDTO;
        }).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public AccountNormalDTO getAccountNormalById(String accountNormalID) {
        AccountNormal accountNormal = accountNormalRepository.findById(accountNormalID).orElse(null);
        assert accountNormal != null;
        AccountNormalDTO accountNormalDTO = modelMapper.map(accountNormal, AccountNormalDTO.class);
        return accountNormalDTO;
    }
}
