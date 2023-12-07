package com.example.backend.services;

import com.example.backend.dto.request.DetailExaminationCreateRequest;
import com.example.backend.dto.request.DetailExaminationUpdateRequest;
import com.example.backend.dto.response.DetailExaminationsResponse;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DetailExaminations;
import com.example.backend.entities.Examinations;
import com.example.backend.entities.Officer;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.DetailExaminationsRepository;
import com.example.backend.repositories.ExaminationRepository;
import com.example.backend.repositories.OfficerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetailExaminationsService {
    @Autowired
    private DetailExaminationsRepository detailExaminationsRepository;
    @Autowired
    private OfficerRepository officerRepository;
    @Autowired
    private ExaminationRepository examinationRepository;

    public DetailExaminations createDetailExaminations(DetailExaminationCreateRequest detailExaminationCreateRequest) {
        Officer officer = officerRepository.findById(detailExaminationCreateRequest.getOfficerId())
                .orElseThrow(() -> new EntityNotFoundException("detailExaminationCreateRequest not found with ID: " + detailExaminationCreateRequest.getOfficerId()));

        Examinations examinations = examinationRepository.findById(detailExaminationCreateRequest.getExaminationsId())
                .orElseThrow(() -> new EntityNotFoundException("detailExaminationCreateRequest not found with ID: " + detailExaminationCreateRequest.getExaminationsId()));

        DetailExaminations detailExaminations = new DetailExaminations();
        detailExaminations.setOfficer(officer);
        detailExaminations.setExaminations(examinations);
        detailExaminations.setRole(detailExaminationCreateRequest.getRole());

        return detailExaminationsRepository.save(detailExaminations);
    }

    public Optional<DetailExaminations> updateDetailExaminations(DetailExaminationUpdateRequest detailExaminationUpdateRequest) {
        Officer officer = officerRepository.findById(detailExaminationUpdateRequest.getOfficerId())
                .orElseThrow(() -> new EntityNotFoundException("detailExaminationUpdateRequest not found with ID: " + detailExaminationUpdateRequest.getOfficerId()));

        Examinations examinations = examinationRepository.findById(detailExaminationUpdateRequest.getExaminationsId())
                .orElseThrow(() -> new EntityNotFoundException("detailExaminationUpdateRequest not found with ID: " + detailExaminationUpdateRequest.getExaminationsId()));

        return detailExaminationsRepository.findById(detailExaminationUpdateRequest.getId()).map(update -> {
            if (detailExaminationUpdateRequest.getOfficerId() != 0)
                update.setOfficer(officer);
            if (detailExaminationUpdateRequest.getExaminationsId() != 0)
                update.setExaminations(examinations);
            if(Objects.nonNull(detailExaminationUpdateRequest.getRole())) {
                update.setRole(detailExaminationUpdateRequest.getRole());
            }
            return detailExaminationsRepository.save(update);
        });
    }

    public List<DetailExaminationsResponse> DetailExaminationsList() {
         return detailExaminationsRepository.findAll()
                .stream()
                .map(tmp -> {
                    DetailExaminationsResponse response = new DetailExaminationsResponse();
                    response.setId(tmp.getId());
                    Officer officer = tmp.getOfficerId();
                    if (officer != null) {
                        response.setOfficerId(officer.getId());
                        response.setName(officer.getName());
                        response.setPhone(officer.getPhone());
                        response.setEmail(officer.getEmail());
                    }
                    Examinations examinations = tmp.getExaminationsId();
                    if (examinations != null) {
                        response.setExaminationsId(examinations.getId());
                        response.setExaminationsName(examinations.getExaminationsName());
                        response.setExaminationsDate(examinations.getExaminationsDate());
                        response.setExaminationsDescription(examinations.getExaminationsDescription());
                    }
                    response.setRole(tmp.getRole());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public Optional<DetailExaminationsResponse> findDetailExaminationsById(int id) {
        var result = detailExaminationsRepository.findById(id)
                .map(tmp -> {
                    DetailExaminationsResponse response = new DetailExaminationsResponse();
                    response.setId(tmp.getId());
                    Officer officer = tmp.getOfficerId();
                    if (officer != null) {
                        response.setOfficerId(officer.getId());
                        response.setName(officer.getName());
                        response.setPhone(officer.getPhone());
                        response.setEmail(officer.getEmail());
                    }
                    Examinations examinations = tmp.getExaminationsId();
                    if (examinations != null) {
                        response.setExaminationsId(examinations.getId());
                        response.setExaminationsName(examinations.getExaminationsName());
                        response.setExaminationsDate(examinations.getExaminationsDate());
                        response.setExaminationsDescription(examinations.getExaminationsDescription());
                    }
                    response.setRole(tmp.getRole());
                    return response;
                });
        if (result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }

        return result;
    }

    public ResponseMessage deleteDetailExaminationsById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = detailExaminationsRepository.findById(id);
        if(isResult.isPresent()){
            detailExaminationsRepository.deleteById(id);
            message.setMessage("delete detail examination by id successfully!");
        }else{
            message.setMessage("delete detail examination by id failed!");
        }
        return message;
    }
}
