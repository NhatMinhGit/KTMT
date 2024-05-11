package com.example.demo.repositories;

import com.example.demo.entities.AccountNormal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNormalRepository extends JpaRepository<AccountNormal, String> {
}
