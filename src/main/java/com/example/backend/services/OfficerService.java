package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Officer;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.exceptions.CustomExceptionHandler;
import com.example.backend.repositories.OfficerRepository;
import com.example.backend.utils.NonNullPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OfficerService {
    @Autowired
    private OfficerRepository officerRepository;


    public Officer createOfficer(Officer officer) {
        return officerRepository.save(officer);

    }

    public Optional<Officer> updateOfficer(Officer officer) {
        return officerRepository.findById(officer.getId()).map(result -> {
            NonNullPropertiesUtils.copyNonNullProperties(officer, result);
            return officerRepository.save(result);
            });
    }

    public Optional<Officer> findOfficerById(int id){
        var result = officerRepository.findById(id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public List<Officer> officerList() {
         return officerRepository.findAll();
    }

    public ResponseMessage deleteProfileById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = officerRepository.findById(id);
        if(isResult.isPresent()){
            officerRepository.deleteById(id);
            message.setMessage("delete officer by id successfully!");
        }else{
            message.setMessage("delete officer by id failed!");
        }
        return message;
    }
}
