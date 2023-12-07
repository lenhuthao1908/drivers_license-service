package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Examinations;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.ExaminationRepository;
import com.example.backend.utils.NonNullPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExaminationService {
    @Autowired
    private ExaminationRepository examinationRepository;

    public Examinations createExamination(Examinations examination) {
        return examinationRepository.save(examination);
    }

    public Optional<Examinations> updateExamination(Examinations examination) {
        return examinationRepository.findById(examination.getId()).map(update -> {
            NonNullPropertiesUtils.copyNonNullProperties(examination, update);
            return examinationRepository.save(update);
        });
    }

    public Optional<Examinations> findExaminationById(int id) {
        var result = examinationRepository.findById(id);
        if (result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public List<Examinations> examinationsList() {
        var result = examinationRepository.findAll();
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_GET_ALL_LIST);
        }
        return result;
    }

    public ResponseMessage deleteExaminationById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = examinationRepository.findById(id);
        if(isResult.isPresent()){
            examinationRepository.deleteById(id);
            message.setMessage("delete examination by id successfully!");
        }else{
            message.setMessage("delete examination by id failed!");
        }
        return message;
    }
}
