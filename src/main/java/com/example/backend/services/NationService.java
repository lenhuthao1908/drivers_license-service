package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Nation;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.NationRepository;
import com.example.backend.utils.NonNullPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NationService {
    @Autowired
    private NationRepository nationRepository;

    public Nation createNation(Nation nation) {
        return nationRepository.save(nation);
    }

    public Optional<Nation> updateNation(Nation nation) {
        return nationRepository.findById(nation.getId()).map(update -> {
            NonNullPropertiesUtils.copyNonNullProperties(nation, update);
            return nationRepository.save(update);
        });
    }

    public Optional<Nation> findNationById(int id) {
        var result = nationRepository.findById(id);
        if (result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public List<Nation> nationList() {
        return nationRepository.findAll();
    }

    public ResponseMessage deleteNationById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = nationRepository.findById(id);
        if(isResult.isPresent()){
            nationRepository.deleteById(id);
            message.setMessage("delete officer by id successfully!");
        }else{
            message.setMessage("delete officer by id failed!");
        }
        return message;
    }
}
