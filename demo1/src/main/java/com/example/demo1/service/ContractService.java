package com.example.demo1.service;

import com.example.demo1.entity.Contract;
import com.example.demo1.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAllContract(){
        return contractRepository.findAll();
    }

    public void Save(Contract contract){
        contractRepository.save(contract);
    }
}
