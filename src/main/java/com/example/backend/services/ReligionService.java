package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Religion;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.ReligionRepository;
import com.example.backend.utils.NonNullPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReligionService {
    @Autowired
    private ReligionRepository religionRepository;

    public Religion createReligion(Religion religion) {
         return religionRepository.save(religion);
    }

    public Optional<Religion> updateReligion(Religion religion) {
        return religionRepository.findById(religion.getId()).map(result -> {
            NonNullPropertiesUtils.copyNonNullProperties(religion, result);
            return religionRepository.save(result);
        });
    }

    public List<Religion> religionList() {
        return religionRepository.findAll();
    }

    public Optional<Religion> findReligionById(int id) {
        var result = religionRepository.findById(id);
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public ResponseMessage deleteReligionById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = religionRepository.findById(id);
        if(isResult.isPresent()){
            religionRepository.deleteById(id);
            message.setMessage("delete religion by id successfully!");
        }else{
            message.setMessage("delete religion by id failed!");
        }
        return message;
    }
}
