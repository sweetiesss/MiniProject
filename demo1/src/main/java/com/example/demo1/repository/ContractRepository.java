package com.example.demo1.repository;

import com.example.demo1.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, String> {
}
