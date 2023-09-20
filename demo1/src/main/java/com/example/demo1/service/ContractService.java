package com.example.demo1.service;

import com.example.demo1.entity.Apartment;
import com.example.demo1.entity.Contract;
import com.example.demo1.entity.Customer;
import com.example.demo1.helper.CSVHelper;
import com.example.demo1.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> getAllContract(){
        return contractRepository.findAll();
    }

    public void Save(List<Contract> currContract, Contract contract){
        if(!duplicateCheck(contract, currContract))contractRepository.save(contract);
    }

    public void save(MultipartFile file, List<Contract> currContract) {
        try {
            List<Contract> contractList = CSVHelper.CSVContract(file.getInputStream(), currContract);
            contractRepository.saveAll(contractList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public boolean duplicateCheck(Contract contract, List<Contract> currContract){
        boolean dup = false;

        for(Contract cont : currContract)
            if(cont.getApartmentID().equals(contract.getApartmentID()) &&
                    cont.getCustomerID().equals(contract.getCustomerID()) &&
                    cont.getStartDate().equals(contract.getStartDate()) &&
                    cont.getEndDate().equals(contract.getEndDate())){
                dup = true;
                break;
            }
        return dup;
    }
}
